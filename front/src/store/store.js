import { createStore } from 'vuex'
import userService from '@/services/userService'
import { emitter } from '@/socketjs/SocketClient'

export default createStore({
  state: {
    authenticated: false,
    user: {
      id: null,
      username: null,
      imagePath: null
    },
    selectedChat: null,
    chats: []
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
    },
    pushChat(state, chat) {
      state.chats.push(chat)
    },
    selectChat(state, chat) {
      state.selectedChat = chat
    }
  },
  actions: {
    getMyData({ commit }) {
      userService.getMyData()
        .then(res => {
          console.log('[GetMyData]', res.data)
          emitter.emit('onReceiveWSToken', res.data.sockToken)
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
