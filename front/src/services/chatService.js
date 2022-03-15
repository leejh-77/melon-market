import axios from 'axios'

export default {
  getChatList(postId) {
    console.log('[ChatService] get chat list : postid -', postId)
    return axios.get('/api/chats?postId=' + postId)
  }
}
