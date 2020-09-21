import E from 'wangeditor'
import boot from "@/utils/boot";
import {getToken} from "@/utils/auth";
import wangEditorVideo from "@/utils/wangEditorVideo";

export default {
  name: "editor",
  data() {
    return {
      editorContent: '',
      loading: false,
      editor: null
    }
  },
  props: {
    defaultContent: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  mounted() {
    this.editorContent = this.defaultContent
    const Vue = this;
    this.editor = new E(this.$refs.editorDom)
    wangEditorVideo(Vue, this.editor)
    // 设置默认内容
    this.editor.txt.html(this.editorContent)
    // 是否禁用编辑器
    this.editor.$textElem.attr('contenteditable', !this.disabled)
  },
  methods: {

  },
  watch: {
    editorContent(val) {
      this.$emit('update:defaultContent', val)
    },
    defaultContent(val) {
      this.editorContent = val
      this.editor.txt.html(this.editorContent)
    }
  }
}
