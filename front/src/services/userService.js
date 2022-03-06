import axios from 'axios'

export default {
  login(emailAddress, password) {
    return axios.post('/api/login', {
      username: emailAddress,
      password: password
    })
  },
  signup(emailAddress, username, password) {
    return axios.post('/api/register', {
      emailAddress: emailAddress,
      username: username,
      password: password
    })
  }
}
