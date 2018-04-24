import Vue from 'vue'
import Vuex from 'vuex'
import {users} from '@/api/api'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    accessToken: null,
    email: null,
    name: null,
    id: null
  },
  getters: {},
  mutations: {
    LOGIN (state, data) {
      state.accessToken = data.token
      state.email = data.email
      state.name = data.username
      state.id = data.id
      console.log(state)
    }
  },
  actions: {
    LOGIN ({commit}, params) {
      return users.signIn(params)
        .then((res) => {
          commit('LOGIN', res)
        })
    }
  }
})
