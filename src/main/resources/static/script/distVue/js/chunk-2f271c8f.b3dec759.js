(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2f271c8f"],{"0aad":function(t,e,n){"use strict";n("a434");var i=n("5530"),a=n("7fd5");e["a"]={components:{addRow:a["a"]},data:function(){return{loading:!1,rowData:{},rowIndex:0,dialogVisible:!1,stimulateTypeName:{TEXT:"文字",IMG:"图片",AUDIO_AND_VIDEO:"音视频"}}},props:{nextData:{type:Array,default:function(){return[]}},disabled:{type:Boolean,default:!1},dels:{type:Array,default:function(){return[]}},experimentType:{type:String,default:"NO_FEEDBACK"}},provide:function(){return{experimentTypeTxt:this.experimentType}},created:function(){console.log(111,this.experimentType)},methods:{getStimulateTypeName:function(t){return this.stimulateTypeName[t]||"-"},setListData:function(t){this.dataList=t},addRow:function(t,e){var n=Object(i["a"])({},e[t]);n.id="",n.createDate="",n.modifyDate="",e.splice(t+1,0,n)},deleteRow:function(t,e){e.length>1&&e.splice(t,1)},rowClick:function(t,e){this.rowData=e[t],this.rowIndex=t,this.dialogVisible=!0},rowDataChange:function(t){var e=t.data;this.dialogVisible=!1,this.$set(this.dataList,this.rowIndex,e)}},watch:{dataList:{handler:function(t){this.$emit("update:next-data",t)},deep:!0},nextData:{handler:function(t){this.dataList=t},deep:!0}}}},"38b1":function(t,e,n){},6615:function(t,e,n){"use strict";n.d(e,"a",(function(){return r}));n("d3b7");var i=n("9179"),a=n("4328"),o=n.n(a);function r(t){var e="/feedback/random/info";return new Promise((function(n,a){i["a"].post(e,{},{"Content-Type":"application/x-www-form-urlencoded"},o.a.stringify(t)||{}).then((function(t){n(t)})).catch((function(t){a(t),new Error(t)}))}))}},"6ab8":function(t,e,n){"use strict";n.d(e,"a",(function(){return a}));n("d3b7");var i=n("9179");function a(t){var e="/feedback/saveAndEdit";return new Promise((function(n,a){i["a"].post(e,{},{},t||{}).then((function(t){n(t)})).catch((function(t){a(t),new Error(t)}))}))}},"6bef":function(t,e,n){"use strict";n("99af"),n("4160"),n("a9e3"),n("d3b7"),n("25f0"),n("159b");var i=n("5f87"),a=n("b841"),o=n("c302");function r(t,e){return'\n    <div id="'.concat(t,'" class="w-e-up-btn" style="text-align: center;cursor: pointer;">\n        <i class="w-e-icon-upload2" style="font-size: 60px"></i>\n    </div>\n    <div style="display:none;">\n        <input id="').concat(e,'" type="file" accept="video/*">\n    </div>\n  ')}function c(t,e){e.customConfig.onchange=function(e){t.editorContent=e},e.customConfig.uploadImgShowBase64=!0,e.customConfig.uploadImgServer="file/editorFileUpload",e.customConfig.uploadFileName="file",e.customConfig.uploadImgMaxLength=1,e.customConfig.uploadImgHeaders={Authorization:Object(i["a"])()},e.customConfig.uploadImgHooks={customInsert:function(t,e){var n=e.data,i=[];n.length&&n.forEach((function(t){i.push("".concat(o["a"]).concat(t))})),t(i)}},e.create();var n=setTimeout((function(){e.$toolbarElem[0].children[16].addEventListener("click",(function(n){var i=document.querySelector(".w-e-panel-container"),c=i.querySelector(".w-e-panel-tab-content > div");i.querySelector(".block").style.display="none";var l=Number(Math.random().toString().substr(3,3)+Date.now()).toString(36),s=Number(Math.random().toString().substr(3,3)+Date.now()).toString(36);c.querySelector(".w-e-button-container").style.display="none",c.insertAdjacentHTML("afterbegin",r(l,s));var d=document.createEvent("MouseEvents");d.initEvent("click",!0,!0);var u=document.getElementById(s);document.getElementById(l).addEventListener("click",(function(){u.dispatchEvent(d)}),!1),u.addEventListener("change",(function(n){n.preventDefault();var r=new FormData,c=Math.round(n.target.files[0].size/1024/1024*100)/100;if(c>100)return alert("请上传100MB以内的视频文件"),void i.querySelector(".w-e-panel-close").dispatchEvent(d);r.append("file",n.target.files[0]),t.loading=!0,Object(a["a"])(r).then((function(n){t.loading=!1,u.value="",e.cmd.do("insertHTML",'<p><iframe style="width: 552px;min-height: 314px;margin: 0 auto;display: block;" src="'.concat(o["a"]).concat(n.path,'"></iframe></p>')),i.querySelector(".w-e-panel-close").dispatchEvent(d)})).catch((function(t){alert(t)}))}),!1)}),!1)}),20);t.$once("hook:beforeDestroy",(function(){clearTimeout(n)}))}e["a"]=c},"85eb":function(t,e,n){"use strict";var i=n("38b1"),a=n.n(i);a.a},b346:function(t,e,n){"use strict";n.d(e,"c",(function(){return i})),n.d(e,"a",(function(){return a})),n.d(e,"b",(function(){return o}));n("7db0"),n("b64b"),n("ac1f"),n("1276");function i(t){return Object.keys(t).length}function a(t,e){return Math.floor(Math.random()*(e-t+1)+t)}function o(t){var e="",n="";try{var i=t.split(".");e=i[i.length-1]}catch(p){e=""}if(!e)return!1;e=e.toLocaleLowerCase();var a=["png","jpg","jpeg","bmp","gif"];if(n=a.find((function(t){return t===e})),n)return"image";var o=["txt"];if(n=o.find((function(t){return t===e})),n)return"txt";var r=["xls","xlsx"];if(n=r.find((function(t){return t===e})),n)return"excel";var c=["doc","docx"];if(n=c.find((function(t){return t===e})),n)return"word";var l=["pdf"];if(n=l.find((function(t){return t===e})),n)return"pdf";var s=["ppt","pptx"];if(n=s.find((function(t){return t===e})),n)return"ppt";var d=["mp4","m2v","mkv","rmvb","wmv","avi","flv","mov","m4v"];if(n=d.find((function(t){return t===e})),n)return"video";var u=["mp3","wav","wmv"];return n=u.find((function(t){return t===e})),n?"radio":"other"}},b841:function(t,e,n){"use strict";n.d(e,"a",(function(){return a}));n("d3b7");var i=n("9179");function a(t){var e="/file/fileUpload";return new Promise((function(n,a){i["a"].uploadFile(e,{"Content-Type":"application/x-www-form-urlencoded"},t).then((function(t){n(t)})).catch((function(t){a(t),new Error(t)}))}))}},b9d8:function(t,e,n){"use strict";n.r(e);var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{attrs:{id:"no-feedback"}},[n("div",{staticClass:"centent-webkit"},[n("div",{staticClass:"form-centent-webkit"},[n("startInstruction",{attrs:{"next-data":t.homeForm,disabled:t.locking,experimentType:t.experimentType},on:{"update:nextData":function(e){t.homeForm=e},"update:next-data":function(e){t.homeForm=e}}}),n("div",{staticClass:"home-table-webkit",staticStyle:{"margin-top":"10px"}},[n("div",{staticStyle:{"margin-top":"20px"}},[t._v(" 是否显示进度条 "),n("el-radio-group",{attrs:{disabled:t.locking},model:{value:t.yesOrNoViewProgressBar,callback:function(e){t.yesOrNoViewProgressBar=e},expression:"yesOrNoViewProgressBar"}},[n("el-radio",{attrs:{label:"NO",border:""}},[t._v("关闭")]),n("el-radio",{attrs:{label:"YES",border:""}},[t._v("开启")])],1)],1),n("div",{staticStyle:{display:"flex","align-items":"center",margin:"10px 0"}},[n("span",{staticStyle:{"margin-right":"10px"}},[t._v("选择屏幕颜色")]),n("el-color-picker",{staticStyle:{"vertical-align":"top"},attrs:{disabled:t.locking},model:{value:t.screenColor,callback:function(e){t.screenColor=e},expression:"screenColor"}})],1),n("div",{staticStyle:{display:"flex","align-items":"center",margin:"10px 0"}},[n("span",{staticStyle:{"margin-right":"10px"}},[t._v("选择实验刺激（文字）的颜色")]),n("el-color-picker",{staticStyle:{"vertical-align":"top"},attrs:{disabled:t.locking},model:{value:t.fontColor,callback:function(e){t.fontColor=e},expression:"fontColor"}})],1),n("div",{staticStyle:{display:"flex","align-items":"center",margin:"10px 0"}},[n("span",{staticStyle:{"margin-right":"10px"}},[t._v("选择实验刺激（文字）的大小")]),n("el-select",{attrs:{disabled:t.locking,placeholder:"请选择"},model:{value:t.fontSize,callback:function(e){t.fontSize=e},expression:"fontSize"}},t._l(t.fontSizeData,(function(t){return n("el-option",{key:t.value,attrs:{label:t.label,value:t.value}})})),1),n("span",{staticStyle:{"margin-left":"10px"},style:{fontSize:t.fontSize+"px"}},[t._v(t._s(t.fontSize+"px"))])],1),n("div",{staticStyle:{display:"flex","align-items":"center",margin:"20px 0 30px 0"}},[n("span",{staticStyle:{"margin-right":"10px"}},[t._v("请选择要记录的被试基本信息")]),n("el-checkbox-group",{attrs:{disabled:t.locking},model:{value:t.participantBasicInfo,callback:function(e){t.participantBasicInfo=e},expression:"participantBasicInfo"}},[n("el-checkbox",{attrs:{label:"name"}},[t._v("姓名")]),n("el-checkbox",{attrs:{label:"sex"}},[t._v("性别")]),n("el-checkbox",{attrs:{label:"age"}},[t._v("年龄")]),n("el-checkbox",{attrs:{label:"yearsOfEducation"}},[t._v("受教育年限")]),n("el-checkbox",{attrs:{label:"sexualOrientation"}},[t._v("性取向")]),n("el-checkbox",{attrs:{label:"height"}},[t._v("身高")]),n("el-checkbox",{attrs:{label:"bodyWeight"}},[t._v("体重")])],1)],1),n("div",[t._v(" 是否随机呈现 "),n("el-radio-group",{attrs:{disabled:t.locking},model:{value:t.yesOrNoRandom,callback:function(e){t.yesOrNoRandom=e},expression:"yesOrNoRandom"}},[n("el-radio",{attrs:{label:"NO",border:""}},[t._v("不随机")]),n("el-radio",{attrs:{label:"YES",border:""}},[t._v("随机")])],1)],1),n("div",{staticStyle:{display:"flex","align-items":"center",margin:"13px 0"}},[t._v(" 实验刺激呈现完之后再出现选项 "),n("el-radio-group",{staticStyle:{"margin-left":"10px"},attrs:{disabled:t.locking},model:{value:t.yesOrNoShowFirstStimulate,callback:function(e){t.yesOrNoShowFirstStimulate=e},expression:"yesOrNoShowFirstStimulate"}},[n("el-radio",{attrs:{label:"NO",border:""}},[t._v("否")]),n("el-radio",{attrs:{label:"YES",border:""}},[t._v("是")])],1)],1),n("h2",{staticClass:"home-form-title"},[t._v("作您的实验刺激，实验参数表")]),n("div",[t.infoId?t._e():n("el-button",{staticStyle:{"margin-bottom":"10px",width:"100%"},attrs:{type:"warning"},on:{click:t.dowDownloadExcel}},[t._v("下载模板（若您需要批量设置）")]),t.infoId?t._e():n("el-button",{staticStyle:{"margin-bottom":"10px",width:"100%","margin-left":"0"},attrs:{type:"primary"},on:{click:function(e){return t.$refs.uploadExcel.click()}}},[t._v("导入您制作好的模板")])],1),n("div",{staticClass:"ont-tabs-webkit",staticStyle:{"margin-top":"10px"}},[n("listDataEditor",{ref:"listDataEditor",attrs:{"next-data":t.listDataEditorData,dels:t.childIds,"experiment-type":t.experimentType,disabled:t.locking},on:{"update:nextData":function(e){t.listDataEditorData=e},"update:next-data":function(e){t.listDataEditorData=e},"update:dels":function(e){t.childIds=e}}})],1),n("div",{staticStyle:{display:"flex","align-items":"center",margin:"10px 0"}},[t._v(" 反应设置 "),n("el-radio-group",{staticStyle:{"margin-left":"10px"},attrs:{disabled:t.locking},model:{value:t.immediatelyOver,callback:function(e){t.immediatelyOver=e},expression:"immediatelyOver"}},[n("el-radio",{attrs:{label:"NO",border:""}},[t._v("按键后，实验刺激直至该试次到达设定时间消失")]),n("el-radio",{attrs:{label:"YES",border:""}},[t._v("一旦按键，实验刺激立即消失，该试次结束")])],1)],1)]),n("endInstruction",{attrs:{"next-data":t.endInstruction,disabled:t.locking},on:{"update:nextData":function(e){t.endInstruction=e},"update:next-data":function(e){t.endInstruction=e}}}),n("div",{staticStyle:{"text-align":"right","margin-top":"10px"}},[t.locking?t._e():n("el-button",{attrs:{type:"primary",disabled:t.locking},on:{click:t.updateAjax}},[t._v("保存")])],1)],1)]),n("div",{staticStyle:{height:"10px"}}),n("input",{ref:"uploadExcel",staticStyle:{display:"none"},attrs:{type:"file",accept:".xls,.xlsx"},on:{change:t.uploadExcel}})])},a=[],o=(n("4160"),n("d3b7"),n("ac1f"),n("25f0"),n("3ca3"),n("5319"),n("1276"),n("159b"),n("ddb0"),n("2b3d"),n("5530")),r=n("2f62"),c=n("5c96"),l=n("6ab8"),s=n("6615"),d=n("636a"),u=n("f86f"),p=n("50fa"),f=n("40af"),m=n("9179");function h(t){var e="/experiment/stimulate/importExcel";return new Promise((function(n,i){m["a"].uploadFile(e,{"Content-Type":"application/x-www-form-urlencoded"},t).then((function(t){n(t)})).catch((function(t){i(t),new Error(t)}))}))}function y(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{},e="/file/downloadExcel";return new Promise((function(n,i){m["a"].downloadExcel(e,{"Content-Type":"application/x-www-form-urlencoded"},t).then((function(t){n(t)})).catch((function(t){i(t),new Error(t)}))}))}var b=null,g={name:"noFeedback",computed:Object(o["a"])({},Object(r["b"])(["name"])),data:function(){return{locking:!1,experimentType:"NO_FEEDBACK",homeForm:{},stimulateType:"TEXT",originalStimulateType:"",originalFeedbacks:[],textData:[],imgData:[],audioVideoData:[],listDataEditorData:[],endInstruction:{},childIds:[],yesOrNoViewProgressBar:"NO",screenColor:"#FFF",fontColor:"#000",fontSize:14,participantBasicInfo:[],yesOrNoRandom:"NO",yesOrNoShowFirstStimulate:"NO",immediatelyOver:"NO",fontSizeData:[]}},props:{infoId:{type:String,default:""},lockingOut:{type:Boolean,default:!1},loading:{type:Boolean,default:!1},experimentTypePop:{type:String,default:"NO_FEEDBACK"}},created:function(){this.experimentType=this.experimentTypePop;for(var t=0;t<60;t++)t%2&&t>0&&this.fontSizeData.push({value:t+1,label:"".concat(t+1,"px")});this.infoId&&(this.locking=this.lockingOut,b=this.$loading({lock:!0,text:"页面加载中",background:"rgba(0, 0, 0, 0.7)"}),this.getInfo())},methods:{dowDownloadExcel:function(){var t=this;y().then((function(e){t.download(e,"exp_template.xls")}))},download:function(t,e){var n=new Blob([t],{type:"application/vnd.ms-excel"}),i=document.createElement("a"),a=window.URL.createObjectURL(n),o=e;i.href=a,i.download=o,i.click(),window.URL.revokeObjectURL(a)},uploadExcel:function(t){var e=this,n=new FormData;n.append("file",t.target.files[0]),n.append("type",this.stimulateType),n.append("experimentType",this.experimentType);var i=this.$loading({lock:!0,text:"上传中",background:"rgba(0, 0, 0, 0.7)"});h(n).then((function(t){i.close(),e.$refs.uploadExcel.value="",t.status?e.$refs.listDataEditor.setListData(t.data):e.$message.error(t.message)})).catch((function(t){console.log(t)}))},getInfo:function(){var t=this;Object(s["a"])({id:this.infoId}).then((function(e){if(b.close(),200===e.code){var n=e.data,i=n.experimentName,a=n.whetherFullScreen,o=n.instructionType,r=n.instruction,c=n.instructionDuration,l=n.feedbackExperimentStimulate,s=e.data,d=s.conclusion,u=s.conclusionType,p=s.conclusionDuration,f=(s.experimentType,e.data.stimulateType),m=e.data,h=m.yesOrNoViewProgressBar,y=m.screenColor,g=m.fontColor,v=m.fontSize,x=m.participantBasicInfo,w=m.immediatelyOver,S=m.yesOrNoRandom,k=m.yesOrNoShowFirstStimulate;t.stimulateType=f,t.updateStimulateType=f,t.originalFeedbacks=l,t.yesOrNoViewProgressBar=h,t.screenColor=y,t.fontColor=g,t.fontSize=v,t.immediatelyOver=w,t.yesOrNoRandom=S,t.yesOrNoShowFirstStimulate=k,t.participantBasicInfo=x?x.split(","):[],t.homeForm={experimentName:i,whetherFullScreen:a,instructionType:o,instruction:r,instructionDuration:c},t.listDataEditorData=l,t.endInstruction={conclusion:d,conclusionType:u,conclusionDuration:p}}}))},updateAjax:function(){var t=this,e=[];this.updateStimulateType!==this.stimulateType&&(this.childIds=[],this.originalFeedbacks.forEach((function(e){t.childIds.push(e.id)}))),e=this.listDataEditorData,e.forEach((function(t,e){t.code=e})),Object(l["a"])(Object(o["a"])(Object(o["a"])(Object(o["a"])({id:this.infoId,experimentType:this.experimentType},this.homeForm),{},{yesOrNoViewProgressBar:this.yesOrNoViewProgressBar,screenColor:this.screenColor,fontColor:this.fontColor,fontSize:this.fontSize,participantBasicInfo:this.participantBasicInfo.toString(),yesOrNoRandom:this.yesOrNoRandom,yesOrNoShowFirstStimulate:this.yesOrNoShowFirstStimulate,immediatelyOver:this.immediatelyOver,feedbackExperimentStimulate:e},this.endInstruction),{},{childIds:this.childIds.toString()})).then((function(e){console.log(e),200===e.code&&"[object Object]"===Object.prototype.toString.call(e.data)?c["MessageBox"].confirm("保存成功,该题目在进行一次答题后将被锁定，无法再次编辑或删除","成功",{confirmButtonText:"确定",cancelButtonText:"取消",modalAppendToBody:!1,type:"warning"}).then((function(){t.$router.replace({name:"Created"})})).catch((function(){})):c["Message"].error(e.desc)}))}},components:{startInstruction:d["a"],endInstruction:u["a"],textEditor:p["a"],listDataEditor:f["a"]},watch:{}},v=g,x=v,w=(n("85eb"),n("2877")),S=Object(w["a"])(x,i,a,!1,null,"d646bd4c",null);e["default"]=S.exports},c302:function(t,e,n){"use strict";n.d(e,"a",(function(){return i}));var i="".concat(window.location.origin,"/upload/")},e8ae:function(t,e,n){"use strict";n.d(e,"a",(function(){return i}));var i={buttonContentButton:[{name:"选项1"},{name:"选项2"}],buttonContentKeyUp:[{name:"是",keyUp:"k"},{name:"否",keyUp:"j"}],buttonContentSlider:{min:0,max:100},buttonContentOrbit:{}}}}]);