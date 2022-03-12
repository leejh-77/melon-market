import axios from 'axios'

export default {
  getRegions(code) {
    if (code == null) {
      return axios.get('/api/regions')
    }
    return axios.get('/api/regions?code=' + code)
  }
}
