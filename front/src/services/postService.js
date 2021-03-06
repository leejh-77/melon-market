import axios from 'axios'
import { Buffer } from 'buffer'

export default {
  getPostList(type, query, regionCode) {
    let params = 'type=' + type
    if (query != null) {
      params += '&query=' + query
    }
    if (regionCode != null) {
      params += '&region=' + regionCode
    }
    return axios.get('/api/posts?' + params)
  },
  getPost(id) {
    return axios.get('/api/posts/' + id)
  },
  getPostImage(url) {
    return axios.get('/api/posts/images/' + url, {
      responseType: 'arraybuffer'
    }).then(res => {
      const base64 = Buffer.from(res.data, 'binary').toString('base64')
      return 'data:image/png;base64, ' + base64
    })
  },
  addPost(data) {
    return axios.post('/api/posts', data, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  },
  updatePost(id, data) {
    return axios.post('/api/posts/' + id, data, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  },
  deletePost(id) {
    return axios.delete('/api/posts/' + id)
  },
  changeLike(id, isLike) {
    const url = '/api/posts/' + id + '/likes'
    return isLike ? axios.post(url) : axios.delete(url)
  }
}
