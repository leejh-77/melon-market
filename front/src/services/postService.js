import axios from 'axios'

export default {
  addPost(data) {
    return axios.post('/api/posts', data, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  },
  getPosts() {
    return axios.get('/api/posts')
  },
  getPostImage(url) {
    return axios.get('/api/posts/images/' + url, {
      responseType: 'arraybuffer'
    })
  },
  getPost(id) {
    return axios.get('/api/posts/' + id)
  }
}
