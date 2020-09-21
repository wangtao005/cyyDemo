import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import registerAjax from "@/api/register";
import loginAjax from "@/api/login";
import infoAjax from "@/api/info";
import forgetAjax from "@/api/forget";
import sloginAjax from "@/api/slogin";
import { MessageBox } from "element-ui";

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: ''
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  }
}

const actions = {
  // user register
  register({ commit }, userInfo) {
    const { username, password, code, id } = userInfo
    registerAjax({
      account: username,
      password: password,
      verificationCodeCheckRequest: {
        account: username,
        code: code,
        id: id
      }
    }).then(res => {
      if (res.code === 200) {
        MessageBox.confirm('注册成功!请登录', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          window.location.reload()
        })
      }
    })
  },
  forget({ commit }, userInfo) {
    const { username, password, code, id } = userInfo
    forgetAjax({
      account: username,
      password: password,
      verificationCodeCheckRequest: {
        account: username,
        code: code,
        id: id
      }
    }).then(res => {
      if (res.code === 200) {
        MessageBox.confirm('修改成功!请登录', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.logout()
          // window.location.reload()
        })
      }
    })
  },
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    loginAjax({
      account: username,
      password: password
    }).then(res => {
      if (res.code === 200) {
        commit('SET_TOKEN', res.data.token)
        setToken(res.data.token)
        window.location.reload()
      } else {
        alert(res.desc)
      }
    })
    // return new Promise((resolve, reject) => {
    //   login({ username: username.trim(), password: password }).then(response => {
    //
    //     resolve()
    //   }).catch(error => {
    //     reject(error)
    //   })
    // })
  },
  slogin({ commit }, userInfo) {
    const { username, code, id } = userInfo
    sloginAjax({
      account: username,
      verificationCodeCheckRequest: {
        account: username,
        code: code,
        id: id
      }
    }).then(res => {
      if (res.code === 200) {
        commit('SET_TOKEN', res.data.token)
        setToken(res.data.token)
        window.location.reload()
      } else {
        // alert(res.msg)
      }
    })
  },
  // get user info
  getInfo({ commit, state }) {
    infoAjax().then(res => {
      if (res.code === 200) {
        const { account, avatar } = res.data
        commit('SET_NAME', account)
        commit('SET_AVATAR', avatar)
      } else {
        // alert(res)
      }
    })
  },

  // user logout
  logout({ commit, state }) {
    removeToken() // must remove  token  first
    resetRouter()
    commit('RESET_STATE')
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

