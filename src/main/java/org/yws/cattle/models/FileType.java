package org.yws.cattle.models;

/**
 * Created by ywszjut on 15/7/27.
 */
public enum FileType {
    FILE(0), FOLDER(1);
    private int type;

    FileType(int type) {
        this.type = type;
    }

    public static FileType fromInteger(int type) {
        return type == 0 ? FileType.FILE : FileType.FOLDER;
    }

}
