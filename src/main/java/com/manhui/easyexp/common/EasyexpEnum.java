package com.manhui.easyexp.common;

public class EasyexpEnum {

    public enum waitTypeEnum {
        FIXED_DURATION(0, "固定时长"), PERIOD(1, "时间段,随机时长"), WAITING_FOR_COMPLETION(2, "等待用户");
        private final int num;
        private final String name;

        waitTypeEnum(int _num, String _name) {
            this.num = _num;
            this.name = _name;
        }
    }

    public enum stimulateTypeEnum {
        TEXT(0, "文字"), IMG(1, "图片"), AUDIO_AND_VIDEO(2, "音视频");
        final int num;
        final String name;

        stimulateTypeEnum(int _num, String _name) {
            this.num = _num;
            this.name = _name;
        }
    }

    public enum trialContentTypeEnum {
        CROSS_GAZE(0, "十字注视点"), BLANK_SCREEN(1, "空白屏");
        final int num;
        final String name;

        trialContentTypeEnum(int _num, String _name) {
            this.num = _num;
            this.name = _name;
        }
    }

    public enum whetherFullScreenEnum {
        FULL_SCREEN(0, "全屏"), NOT_FULL_SCREEN(1, "非全屏");
        final int num;
        final String name;

        whetherFullScreenEnum(int _num, String _name) {
            this.num = _num;
            this.name = _name;
        }
    }

    public enum buttonTypeEnum {
        BUTTON_WITH_OPTIONS(0, "带选项的按钮"), KEYBOARD(1, "键盘"), SLIDER(2, "滑块"), TEXT_INPUT(3, "文字输入"), MOUSE_AND_MOUSE_TRACK(4, "鼠标与鼠标轨迹");

        final int num;
        final String name;

        buttonTypeEnum(int _num, String _name) {
            this.num = _num;
            this.name = _name;
        }

    }

    public enum experimentalLogicTypeEnum {
        REACTION_TIME(0, "根据反应时长来反馈"), LAST_ANSWER(1, "根据被试上一次的回答来给反馈");
        final int num;
        final String name;

        experimentalLogicTypeEnum(int _num, String _name) {
            this.num = _num;
            this.name = _name;
        }
    }

    public enum experimentTypeEnum {
        NO_FEEDBACK(0, "无反馈"), WITH_FEEDBACK(1, "有反馈");
        final int num;
        final String name;

        experimentTypeEnum(int _num, String _name) {
            this.num = _num;
            this.name = _name;
        }
    }
    public enum yesOrNoEnum {
        NO(0, "否"), YES(1, "是");
        final int num;
        final String name;

        yesOrNoEnum(int _num, String _name) {
            this.num = _num;
            this.name = _name;
        }
    }
    public enum positionTypeEnum {
        UP_LEFT_RIGHT(0, "上左右"), CENTER_HORIZONTAL(1, "中横"), CENTER_VERTICAL(2, "中竖");
        final int num;
        final String name;

        positionTypeEnum(int _num, String _name) {
            this.num = _num;
            this.name = _name;
        }
    }

}
