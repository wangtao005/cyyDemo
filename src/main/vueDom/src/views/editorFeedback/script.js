import noFeedback from '@/views/noFeedback/index.vue'
export default {
  name: "editorFeedback",
  components: {
    noFeedback
  },
  data() {
    return {
      infoId: ''
    }
  },
  created() {
    this.infoId = this.$route.params.id
  }
}
