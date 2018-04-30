import axios from 'axios'
import store from '@/store'

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
  insert: function (params, files, defaultImgIdx) {
    debugger
    let formData = new FormData()

    for (let i = 0; i < files.length; i++) {
      formData.append('files', files[i])
    }
    formData.append('defaultImgIdx', 0)
    for (let key in params) {
      formData.append(key, params[key])
    }

    return axios.post(cafes.baseUrl,
      formData,
      {headers: {'Content-Type': 'multipart/form-data'}}
    ).then(res => res.data)
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
      store.dispatch('LOGIN', res.data)
      return 0
    }).catch((e) => {
      return e.response.data.message
    })
  },
  signIn: function (params) {
    return axios.post(`${BASE_URL}/login`, params).then(({data}) => {
      store.dispatch('LOGIN', data)
      return 0
    }).catch((e) => {
      return e.response.data.message
    })
  },
  getInfo: function () {
    return axios.get(this.baseUrl).then(res => {
      return res.data
    })
  }
}

export {cafes, users}
