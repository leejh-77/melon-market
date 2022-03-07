import { createStore } from 'vuex'
import userService from '@/services/userService'

export default createStore({
  state: {
    authenticated: false,
    user: {
      username: null,
      imagePath: null
    }
  },
  getters: {
    authenticated(state) {
      return state.authenticated
    }
  },
  mutations: {
    setUser(state, value) {
      state.user = value
    }
  },
  actions: {
    getMyData({ commit }) {
      userService.getMyData()
        .then(res => {
          commit('setUser', res.data)
        })
        .catch(e => {
          commit('setUser', {
            username: null,
            imagePath: null
          })
        })
    }
  },
  modules: {}
})
