import axios from 'axios'

export default {
  getChatList(postId) {
    return axios.get('/api/chats?postId=' + postId)
  }
}
