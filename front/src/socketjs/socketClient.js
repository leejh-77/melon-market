import SockJS from 'sockjs-client'
import mitt from 'mitt'

class SocketClient {
  constructor() {
    this.socket = null
    this.token = null
    this.onMessageReceived = null
  }

  setToken(token) {
    this.token = token
  }

  connect() {
    this.socket = new SockJS('/chat?token=' + this.token)
    this.socket.onopen = () => {

    }
    this.socket.onmessage = (event) => {
      const data = JSON.parse(event.data)
      console.log('[SocketClient] message received', data)

      if (data.type === 'normal') {
        this.onMessageReceived(data)
      }
    }
    this.socket.onerror = (event) => {

    }
    this.socket.onclose = (event) => {

    }
  }

  send(to, message) {
    console.log('[SocketClient] send message : to - ' + to + ', message - ' + message)
    this.socket.send(JSON.stringify({
      type: 'message',
      content: message,
      to: to
    }))
  }
}

export default new SocketClient()
export const socketEmitter = mitt()
