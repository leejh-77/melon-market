import { createStore } from 'vuex'

export default createStore({
  state: {
    authenticated: false
  },
  getters: {
    isAuthenticated(state) {
      return state.authenticated
    }
  },
  mutations: {
    setAuthenticated(state, value) {
      state.authenticated = value
    }
  },
  actions: {
    setAuthenticated({ commit }, authenticated) {
      commit('setAuthenticated', authenticated)
    }
  },
  modules: {
  }
})
