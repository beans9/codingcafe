import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

const enhanceAccessToeken = () => {
  const {accessToken} = localStorage
  if (!accessToken) return
  axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`
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
    })
  },
  signIn: function (params) {
    return axios.post(`${BASE_URL}/login`, params).then(res => {
      authProc(res.data)
      return 0
    })
  }
}

function authProc (user) {
  localStorage.accessToken = user.token
  localStorage.userName = user.username
  axios.defaults.headers.common['Authorization'] = `${user.token}`
}

export {cafes, users}
