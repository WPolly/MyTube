package com.tcl.lishanwang.mytube.bean;

import java.util.List;

/**
 * Created by xiaoshan on 2016/5/31.
 * 14:38
 */
public class VideoDetailResponseBean {

    /**
     * req_path : /video/getinfo?access_token=AXZ63s0zC1nzr1nrwV8Yqndq46HXUR7yiGwhBOkM6dK1wsDF_ZaKRAvWj9MTfWfnJDgRsN91qKmDEz3r8JOAWrZzNnhXudAUnqfyTIZC1VDMVrBdZ5VR6Yfe1UYukXPUddyK5h4Tm1ubUXQW22UyxTm1IGbSdDkq5oQnuPRwAhA&videoid=E0BZ9n8S6H8
     * response_data : {"author":"PopularMMOs","authorurl":"http://172.24.222.44:3000/UploaderImages/E0BZ9n8S6H8.png","datepublished":"2016- 5-30","description":"Who will survive inside the Burning Notch Land Structures","dislikecount":120,"duration":992,"formats":[{"downloadurl":"","extension":"mp4","itag":22,"progress":0,"resolution":"720p","status":0},{"downloadurl":"","extension":"webm","itag":43,"progress":0,"resolution":"360p","status":0}],"keywords":["minecraft","custom","raining","tnt","battleships","mini-game","games","game","minigames","burning","map","notch land","land","world","theme park","amusement park","mods","mod","modded","1.8","minecraft 1.8","structure","build","building","lets play","minecraft mini-games","challenge","modded mini-game","funny","moments","gamingwithjen","popularmmos","raining tnt","explosion","armor","sword","weapon","minecraft games","minecraft challenges","parkour","song","animation","how to","tutorial","playthrough","minecraft mods","custom map","battleship game"],"likecount":8094,"preview":{"downloadurl":"","extension":"","progress":0,"status":0},"preview_url":"http://172.24.222.44:3000/ThumbnailImages/E0BZ9n8S6H8.png","title":"Minecraft: IT'S RAINING TNT! (BURNING BATTLESHIPS) Mini-Game","videoid":"E0BZ9n8S6H8","viewcount":161668}
     * error : {"error_id":0,"error_desc":"success"}
     */

    public String req_path;
    /**
     * author : PopularMMOs
     * authorurl : http://172.24.222.44:3000/UploaderImages/E0BZ9n8S6H8.png
     * datepublished : 2016- 5-30
     * description : Who will survive inside the Burning Notch Land Structures
     * dislikecount : 120
     * duration : 992
     * formats : [{"downloadurl":"","extension":"mp4","itag":22,"progress":0,"resolution":"720p","status":0},{"downloadurl":"","extension":"webm","itag":43,"progress":0,"resolution":"360p","status":0}]
     * keywords : ["minecraft","custom","raining","tnt","battleships","mini-game","games","game","minigames","burning","map","notch land","land","world","theme park","amusement park","mods","mod","modded","1.8","minecraft 1.8","structure","build","building","lets play","minecraft mini-games","challenge","modded mini-game","funny","moments","gamingwithjen","popularmmos","raining tnt","explosion","armor","sword","weapon","minecraft games","minecraft challenges","parkour","song","animation","how to","tutorial","playthrough","minecraft mods","custom map","battleship game"]
     * likecount : 8094
     * preview : {"downloadurl":"","extension":"","progress":0,"status":0}
     * preview_url : http://172.24.222.44:3000/ThumbnailImages/E0BZ9n8S6H8.png
     * title : Minecraft: IT'S RAINING TNT! (BURNING BATTLESHIPS) Mini-Game
     * videoid : E0BZ9n8S6H8
     * viewcount : 161668
     */

    public ResponseDataBean response_data;
    /**
     * error_id : 0
     * error_desc : success
     */

    public ErrorBean error;

    public static class ResponseDataBean {
        public String author;
        public String authorurl;
        public String datepublished;
        public String description;
        public int dislikecount;
        public int duration;
        public int likecount;
        /**
         * downloadurl :
         * extension :
         * progress : 0
         * status : 0
         */

        public PreviewBean preview;
        public String preview_url;
        public String title;
        public String videoid;
        public int viewcount;
        /**
         * downloadurl :
         * extension : mp4
         * itag : 22
         * progress : 0
         * resolution : 720p
         * status : 0
         */

        public List<FormatsBean> formats;

        public static class PreviewBean {
            public String downloadurl;
            public String extension;
            public int progress;
            public int status;
        }

        public static class FormatsBean {
            public String downloadurl;
            public String extension;
            public int itag;
            public int progress;
            public String resolution;
            public int status;
        }
    }

    public static class ErrorBean {
        public int error_id;
        public String error_desc;
    }
}
