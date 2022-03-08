import axios from 'axios'
import { Buffer } from 'buffer'

export default {
  addPost(data) {
    return axios.post('/api/posts', data, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  },
  getPostList() {
    return axios.get('/api/posts')
  },
  getPostImage(url) {
    return axios.get('/api/posts/images/' + url, {
      responseType: 'arraybuffer'
    }).then(res => {
      const base64 = Buffer.from(res.data, 'binary').toString('base64')
      return 'data:image/jpg;base64, ' + base64
    })
  },
  getPost(id) {
    return axios.get('/api/posts/' + id)
  }
}
