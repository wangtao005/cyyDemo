import { mapGetters } from 'vuex'
import { MessageBox } from "element-ui";
import noFeedbackList from './noFeedbackList/noFeedbackList.vue'
import withFeedbackList from './withFeedbackList/withFeedbackList.vue'
export default {
  name: "createdList",
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  data() {
    return {
      feedbackType: 'noFeedback'
    }
  },
  created() {
    // console.log(this.name)
    if (window.sessionStorage.getItem('feedbackType')) {
      this.feedbackType = window.sessionStorage.getItem('feedbackType')
    }
  },
  methods: {
    examineFeedback(id) {
      this.$router.push({
        name: 'ExamineFeedback',
        params: {
          id: id,
          locking: true
        }
      })
    },
    editFeedback(id) {
      this.$router.push({
        name: 'EditorFeedback',
        params: {
          id: id,
          locking: false
        }
      })
    },
    examineFeedbackWith(id) {
      this.$router.push({
        name: 'ExamineFeedbackWith',
        params: {
          id: id,
          locking: true
        }
      })
    },
    editFeedbackWith(id) {
      this.$router.push({
        name: 'EditorFeedbackWith',
        params: {
          id: id,
          locking: false
        }
      })
    },
    switchUts({name}) {
      window.sessionStorage.setItem('feedbackType', name)
    }
  },
  components: {
    noFeedbackList,
    withFeedbackList
  },
  watch: {
    // homeForm: {
    //   handler(val) {
    //     console.log(val.editorTxt)
    //   },
    //   deep: true
    // }
  }
}
