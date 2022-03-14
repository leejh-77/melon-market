import { createStore } from 'vuex'
import userService from '@/services/userService'
import { socketEmitter } from '@/socketjs/socketClient'

export default createStore({
  state: {
    authenticated: false,
    user: {
      id: null,
      username: null,
      imagePath: null
    },
    selectedChatRoom: null,
    chatRooms: []
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
    pushChatRoom(state, chatRoom) {
      const found = state.chatRooms.find(c => c.id === chatRoom.id)
      if (found != null) {
        return
      }
      state.chatRooms.push(chatRoom)
    },
    selectChatRoom(state, chat) {
      state.selectedChatRoom = chat
    },
    pushMessage(state, message) {
      if (state.selectedChatRoom == null || message.from !== state.selectedChatRoom.id) {
        return
      }
      console.log(state.selectedChatRoom)
      console.log('[Store] push message - ', message)
      state.selectedChatRoom.messages.push({
        id: 11,
        content: message.message
      })
    }
  },
  actions: {
    getMyData({ commit }) {
      userService.getMyData()
        .then(res => {
          console.log('[GetMyData]', res.data)
          socketEmitter.emit('onReceiveWSToken', res.data.sockToken)
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
