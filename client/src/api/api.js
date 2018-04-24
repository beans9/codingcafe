import axios from 'axios'

const BASE_URL = 'http://localhost:8090'

const enhanceAccessToeken = () => {
  const {codingcafeToken} = localStorage
  if (!codingcafeToken) return
  axios.defaults.headers.common['Authorization'] = codingcafeToken
}
enhanceAccessToeken()

let cafes = {
  baseUrl: `${BASE_URL}/cafe`,
  select: function () {
    return axios.get(cafes.baseUrl).then((res) => res.data)
  },
  get: function (id) {
    return axios.get(cafes.baseUrl + '/' + id).then((res) => res.data)
  },
  insert: function (params) {
    return axios.post(cafes.baseUrl, params).then(res => res.data)
  },
  patch: function (params) {
    return axios.patch(cafes.baseUrl + '/' + params.id, params).then(res => res.data)
  },
  del: function (id) {
    return axios.delete(cafes.baseUrl + '/' + id)
  }
}

let users = {
  baseUrl: `${BASE_URL}/users`,
  signUp: function (params) {
    return axios.post(this.baseUrl + '/sign-up', params).then(res => {
      authProc(res.data)
      return 0
    }).catch((e) => {
      return e.response.data.message
    })
  },
  signIn: function (params) {
    return axios.post(`${BASE_URL}/login`, params).then(({data}) => data)
  },
  getInfo: function () {
    return axios.get(this.baseUrl).then(res => {
      return res.data
    })
  }
}

function authProc (user) {
  localStorage.codingcafeToken = user.token
  localStorage.codingcafeEmail = user.email
  localStorage.codingcafeName = user.username
  localStorage.codingcafeId = user.id
  axios.defaults.headers.common['Authorization'] = user.token
}

export {cafes, users}
