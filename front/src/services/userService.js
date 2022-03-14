import axios from 'axios'
import { Buffer } from 'buffer'

export default {
  login(emailAddress, password) {
    return axios.post('/api/login', {
      username: emailAddress,
      password: password
    })
  },
  logout() {
    return axios.post('/api/logout')
  },
  signup(emailAddress, username, password) {
    return axios.post('/api/register', {
      emailAddress: emailAddress,
      username: username,
      password: password
    })
  },
  getMyData() {
    return axios.get('/api/users/me')
  },
  getUser(id) {
    return axios.get('/api/users/' + id)
  },
  updateUser(form) {
    return axios.post('/api/users/me', form, {
      'Content-Type': 'multipart/form-data'
    })
  },
  getUserImage(url) {
    return axios.get('/api/users/images/' + url, {
      responseType: 'arraybuffer'
    }).then(res => {
      const base64 = Buffer.from(res.data, 'binary').toString('base64')
      return 'data:image/png;base64, ' + base64
    })
  }
}
