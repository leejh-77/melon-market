import { createStore } from 'vuex'
import userService from '@/services/userService'

export default createStore({
  state: {
    user: {
      authenticated: false,
      username: null,
      imagePath: null
    }
  },
  getters: {
    getUser(state) {
      return state.user
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
          res.data.authenticated = true
          commit('setUser', res.data)
        })
        .catch(e => {
          commit('setUser', {
            authenticated: false,
            username: null,
            imagePath: null
          })
        })
    }
  },
  modules: {}
})
