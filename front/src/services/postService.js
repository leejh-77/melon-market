import axios from 'axios'
import { Buffer } from 'buffer'
import { ListQuery } from '@/constant'

export default {
  addPost(data) {
    return axios.post('/api/posts', data, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  },
  getPostList(query) {
    let q
    if (query === ListQuery.Recent) {
      q = 'recent'
    } else if (query === ListQuery.Like) {
      q = 'like'
    } else {
      q = 'popular'
    }
    return axios.get('/api/posts?query=' + q)
  },
  getPostImage(url) {
    return axios.get('/api/posts/images/' + url, {
      responseType: 'arraybuffer'
    }).then(res => {
      const base64 = Buffer.from(res.data, 'binary').toString('base64')
      return 'data:image/png;base64, ' + base64
    })
  },
  getPost(id) {
    return axios.get('/api/posts/' + id)
  },
  changeLike(id, isLike) {
    const url = '/api/posts/' + id + '/likes'
    return isLike ? axios.post(url) : axios.delete(url)
  }
}
