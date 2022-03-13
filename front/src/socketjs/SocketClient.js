import SockJS from 'sockjs-client'

class SocketClient {
  constructor() {
    this.socket = null
    this.token = null
  }

  setToken(token) {
    this.token = token
  }

  connect() {
    this.socket = new SockJS('/rt?token=?' + this.token)
  }
}
