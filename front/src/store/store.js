import { createStore } from 'vuex'
import userService from '@/services/userService'
import { emitter } from '@/main'

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
      console.log('[Store] set user', user)
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
    selectChatRoom(state, chatRoom) {
      state.selectedChatRoom = chatRoom
      if (chatRoom != null) {
        chatRoom.hasNewMessage = false
      }
    },
    removeChatRoom(state, roomId) {
      const idx = state.chatRooms.findIndex(r => r.id === roomId)
      if (idx >= 0) {
        state.chatRooms.splice(idx, 1)
      }
    }
  },
  actions: {
    getMyData({ commit }) {
      userService.getMyData()
        .then(res => {
          const data = res.data
          console.log('[GetMyData] response ', data)
          emitter.emit('onReceiveWSToken', data.sockToken)
          commit('setUser', {
            id: data.id,
            username: data.username,
            imageUrl: data.imageUrl
          })
        })
        .catch(e => {
          console.log('[GetMyData] error ', e)
          commit('setUser', {
            username: null,
            imagePath: null
          })
        })
    },
    pushMessage({ commit }, message) {
      const found = this.state.chatRooms.find(room => message.from === room.id)
      if (found == null) {
        console.log('[Store] Get user request : ', message.from)
        userService.getUser(message.from)
          .then(res => {
            const data = res.data
            console.log('[Store] Get user response : ', data)
            commit('pushChatRoom', {
              id: data.id,
              imageUrl: data.imageUrl,
              name: data.username,
              hasNewMessage: true,
              messages: []
            })
          })
        return
      }

      if (this.state.selectedChatRoom == null || this.state.selectedChatRoom.id !== found.id) {
        found.hasNewMessage = true
        return
      }

      console.log('[Store] push message - ', message)
      this.state.selectedChatRoom.messages.push({
        content: message.message
      })
    }
  },
  modules: {}
})
