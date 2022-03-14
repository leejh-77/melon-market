import axios from 'axios'

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
  }
}
