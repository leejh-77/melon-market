import { createStore } from 'vuex'
import userService from '@/services/userService'

export default createStore({
  state: {
    authenticated: false,
    user: {
      id: null,
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
    setUser(state, user) {
      state.user = user
      state.authenticated = user.id != null
    }
  },
  actions: {
    getMyData({ commit }) {
      userService.getMyData()
        .then(res => {
          console.log('[GetMyData]', res.data)
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
