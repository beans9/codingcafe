import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

const localStorageKey = 'ccUserInfo'

// F5 Refresh 할 경우 로컬스토리지에 값이 있다면 재셋팅
const checkLocalStorage = () => {
  let userInfo = localStorage[localStorageKey]
  if (!userInfo) return
  store.dispatch('LOGIN', JSON.parse(userInfo))
}

const store = new Vuex.Store({
  state: {
    userInfo: {
      accessToken: null,
      email: null,
      name: null,
      id: null
    }
  },
  getters: {
    isAuthenticated: state => {
      return state.userInfo.accessToken != null
    },
    getUserInfo: state => {
      return state.userInfo
    }
  },
  mutations: {
    LOGIN (state, data) {
      state.userInfo.accessToken = data.token
      state.userInfo.email = data.email
      state.userInfo.name = data.username
      state.userInfo.id = data.id

      localStorage.setItem(localStorageKey, JSON.stringify(data))
      axios.defaults.headers.common['Authorization'] = data.token
    },
    LOGOUT (state) {
      state.userInfo.accessToken = null
      state.userInfo.email = null
      state.userInfo.name = null
      state.userInfo.id = null

      localStorage.removeItem(localStorageKey)
      axios.defaults.headers.common['Authorization'] = null
    }
  },
  actions: {
    LOGIN ({commit}, data) {
      commit('LOGIN', data)
    },
    LOGOUT ({commit}) {
      commit('LOGOUT')
    }
  }
})
export default store
checkLocalStorage()
