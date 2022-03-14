import SockJS from 'sockjs-client'
import mitt from 'mitt'

class SocketClient {
  constructor() {
    this.socket = null
    this.token = null
  }

  setToken(token) {
    this.token = token
  }

  connect() {
    this.socket = new SockJS('/chat?token=' + this.token)
  }
}

export default new SocketClient()
export const emitter = mitt()
