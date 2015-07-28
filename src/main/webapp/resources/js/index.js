(function () {
    var IndexPage = {
        editor: null,
        scriptView: null,
        initEditor: function () {
            editor = CodeMirror.fromTextArea(document.getElementById("edit-script"), {
                lineNumbers: true,
                mode: 'text/x-hive',
                indentWithTabs: true,
                smartIndent: true,
                matchBrackets: true,
                autofocus: true,
                width: '100%',
                height: '400px'
            });

            scriptView = CodeMirror.fromTextArea(document.getElementById("script-p"), {
                lineNumbers: true,
                mode: 'text/x-hive',
                indentWithTabs: true,
                smartIndent: true,
                matchBrackets: true,
                autofocus: true,
                readOnly: true
            });
        },
        initButtonsEvent: function () {

        },
        initTreeClick: function () {
            JobTree.onFileClick = function (fileId) {
                IndexPage.loadJob(fileId);
            }
        },
        init: function () {
            this.initEditor();
            this.initButtonsEvent();
            this.initTreeClick();
        },
        loadJob: function (fileId) {

        },
        freshJobView: function (jobId) {
            $.post(BASE_PATH + "/jobs/get.do", {id: jobId}, function (data) {

                $("#job-id-td").text(data.id);
                $("#job-type-td").text(data.runType);
                $("#name-td").text(data.name);
                $("#run-type-td").text(data.scheduleType == 1 ? "定时调度" : "依赖调度");
                $("#auto-td").text(data.auto == 0 ? "关闭" : "开启");
                $("#run-time-td").text(data.scheduleType == 1 ? data.cron : data.dependencies);
                //        if(data.script) $("#script-p").html(data.script.replace(/\n/gi, "<br/>").replace(/\r/gi, "<br/>"));
                scriptView.setValue(data.script == null ? "" : data.script);
                $("#viewing-job-input").val(data.id);

                $("#inputName").val(data.name);
                $("#inputScheduleType").val(data.runType);

                if (data.scheduleType == 1) {
                    $("#radioSchedualByTime").prop("checked", true);
                    $("#radioSchedualByDependency").prop("checked", false);
                    $("#inputCron").val(data.cron);
                } else {
                    $("#radioSchedualByTime").prop("checked", false);
                    $("#radioSchedualByDependency").prop("checked", true);
                    $("#dependenciesSel").val(data.dependencies);
                }
                editor.setValue(data.script == null ? "" : data.script);
            });
        },
        refreshHistoryView: function (jobId) {
            $.post(BASE_PATH + "/jobs/history.do", {jobId: jobId}, function (data) {
                $("#history-tbody").html("");

                $.each(data, function (key, his) {

                    var td = "<tr><td>" + his.id + "</td><td>" + his.status + "</td><td>" + his.startTime + "</td><td>" + his.endTime + "</td><td>";
                    td += '<button type="button" class="btn btn-default btn-xs" onclick="viewLog(' + his.id + ')">查看日志</button>';
                    if (his.status == "RUNNING") {
                        td += ',<button type="button" class="btn btn-primary btn-xs" onclick="killJob(' + his.id + ')">取消任务</button>';
                    }
                    td += "</td></tr>"
                    $("#history-tbody").append(td);
                });
            });
        },
        viewLog: function (historyId) {
            if (refreshJobLogTimer != undefined) {
                clearTimeout(refreshJobLogTimer);
                $("#log-his-p").html("");
                document.getElementById('log-div').scrollTop = 0;
            }

            $.post(BASE_PATH + "/jobs/gethistorylog.do", {historyId: historyId}, function (res) {
                $("#log-his-p").html("");
                $("#logModal").modal("show");

                if (res.status == "SUCCESS" || res.status == "FAILED") {
                    var cr = $("#log-his-p").html();
                    var nr = res.log.replace(/\n/g, "<br>");
                    $("#log-his-p").html(nr);
                    document.getElementById('log-div').scrollTop = document.getElementById('log-div').scrollHeight;
                } else {
                    refreshJobLogTimer = setInterval(function () {
                        $.post(BASE_PATH + "/jobs/gethistorylog.do", {historyId: historyId}, function (res) {
                            var cr = $("#log-his-p").html();
                            var nr = res.log.replace(/\n/g, "<br>");
                            if (cr != nr) {
                                $("#log-his-p").html(nr);
                                document.getElementById('log-div').scrollTop = document.getElementById('log-div').scrollHeight;
                            }
                            if (res.status == "SUCCESS" || res.status == "FAILED") {
                                clearTimeout(refreshJobLogTimer);
                                refreshHistoryView($("#viewing-job-input").val());
                            }
                        });

                    }, 1000);
                }

            });
        }

    }

    IndexPage.init();
}());
