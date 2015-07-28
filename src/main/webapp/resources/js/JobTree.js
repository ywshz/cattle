var JobTree = {
    init: function () {
        $.fn.zTree.init($("#tree"), {
            async: {
                enable: true,
                url: BASE_PATH + "/tree/list_files.do",
                autoParam: ["id"],
                otherParam:{"properties":"fileType","directions":"desc"}
            },
            view: {
                selectedMulti: false
            },
            edit: {
                enable: true,
                showRemoveBtn: function (treeId, treeNode) {
                    return treeNode.isParent;
                },
                showRenameBtn: function (treeId, treeNode) {
                    return treeNode.isParent;
                }
            },
            callback: {
                onAsyncSuccess: function (event, treeId, treeNode, msg) {
                    //展开根节点
                    if (treeNode == null) {
                        zTree = $.fn.zTree.getZTreeObj("tree");
                        var rootNode = zTree.getNodes()[0];
                        zTree.expandNode(rootNode, true, true, true);
                    }else{
                        if(treeNode.folder){
                            treeNode.isParent=true;
                        }
                    }
                },
                onClick: OnLeftClick,
                onRename: zTreeOnRename,
                onRemove: zTreeOnRemove,
                beforeRemove: zTreeBeforeRemove,
                //onExpand: zTreeOnExpand,
                onRightClick: rightClick
            }
        });

        this.hideRightClickMenu();
        initContextMenuFunction();
    },
    rMenu: $("#rMenu"),
    showRightClickMenu: function (isRoot, isFolder, x, y) {
        $("#rMenu button").show();
        if (isFolder) {
            //$("#add-group-btn").hide();
            $("#add-job-btn").show();
            $("#add-group-btn").show();
        } else {
            $("#add-group-btn").hide();
            $("#add-job-btn").hide();
        }
        if (isRoot) {
            $("#add-job-btn").show();
            $("#add-group-btn").show();
        }
        $(rMenu).css({"top": y + "px", "left": x + "px", "visibility": "visible"});
        $("body").bind("mousedown", onBodyMouseDown);
        $(window).bind("mousedown", onBodyMouseDown);
    },
    hideRightClickMenu: function () {
        if (rMenu) $(rMenu).css({"visibility": "hidden"});
        $("body").unbind("mousedown", onBodyMouseDown);
        $(window).unbind("mousedown", onBodyMouseDown);
    },
    onFileClick : function(fileId){

    }
};

JobTree.init();

zTree = $.fn.zTree.getZTreeObj("tree");

function rightClick(event, treeId, treeNode) {
    if (treeNode == null) return;
    zTree.selectNode(treeNode);
    JobTree.showRightClickMenu(treeNode.getParentNode() == null, treeNode.isParent, event.clientX, event.clientY)
}

function initContextMenuFunction() {
    $("#add-group-btn").bind("click", {isParent: true}, add);
    $("#add-job-btn").bind("click", {isParent: false}, add);
}

function add(e) {
    JobTree.hideRightClickMenu();

    var zTree = $.fn.zTree.getZTreeObj("tree"),
        isParent = e.data.isParent,
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    if (treeNode) {
        var name = "新文件";
        var type = "File";
        if (isParent) {
            name = "新目录";
            type = "Folder";
            $.post(BASE_PATH + "/jobs/addgroup.do", {"name": name, parentId: treeNode.id}, function (res) {
                if (res.success) {
                    refreshNode("refresh", true);
                } else {
                    alert("Error");
                }
            });
        } else {
            $.post(BASE_PATH + "/jobs/addjob.do", {
                "name": name,
                isParent: isParent,
                "type": type,
                "groupId": treeNode.id
            }, function (res) {
                if (res.success) {
                    refreshNode("refresh", false);
                } else {
                    alert("Error");
                }
            });
        }

    }
}

function refreshNode(type, silent) {
    var zTree = $.fn.zTree.getZTreeObj("tree"),
        nodes = zTree.getSelectedNodes();
    if (nodes.length == 0) {
        alert("请先选择一个父节点");
    }
    for (var i = 0, l = nodes.length; i < l; i++) {
        zTree.reAsyncChildNodes(nodes[i], type, silent);
        if (!silent) zTree.selectNode(nodes[i]);
    }

    //编辑页面的依赖树
//   var dt = $.fn.zTree.getZTreeObj("dependencyTree");
//   dt.reAsyncChildNodes(null , "refresh", true);
}


function OnLeftClick(event, treeId, treeNode) {
    if (!treeNode.isParent) {
        JobTree.onFileClick(treeNode.id);
    }
}



function onBodyMouseDown(event) {
    if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
        $(JobTree.rMenu).css({"visibility": "hidden"});
    }
}

function zTreeOnRename(event, treeId, treeNode, isCancel) {
    $.post(BASE_PATH + "/jobs/updategroupname.do", {id: treeNode.id, name: treeNode.name}, function (res) {
        if (res) alert("重命名成功.");
        else alert("重命名失败，请刷新页面重试.");
    });
}


function zTreeBeforeRemove(treeId, treeNode) {
    return confirm("确认删除？");
}

function zTreeOnRemove(event, treeId, treeNode) {
    $.post(BASE_PATH + "/jobs/deletegroup.do", {id: treeNode.id}, function (res) {
        if (res.success) alert("删除成功.");
        else {
            zTree = $.fn.zTree.getZTreeObj("tree");
            zTree.reAsyncChildNodes(zTree.getNodes()[0], "refresh", false);
            alert(res.message);
        }
    });
}


function zTreeOnExpand(event, treeId, treeNode){
    $.post(BASE_PATH + "/tree/list_files",{parent : treeNode.id, properties:'fileType',directions:'desc'}, function (res) {
        $.each(res, function (key, data) {
            var nodes = zTree.getNodesByParam("id", data);
            for (var i = 0, l = nodes.length; i < l; i++) {
                if (nodes[i].isParent == false) {
                    zTree.setting.view.fontCss = {};
                    zTree.setting.view.fontCss["color"] = "green";
                    zTree.updateNode(nodes[i]);
                    zTree.setting.view.fontCss = {};
                    break;
                }
            }
        });
    });
}