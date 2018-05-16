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
  insert: function (params, files) {
    let formData = new FormData()
    let defaultImgIdx = 0
    for (let i = 0; i < files.length; i++) {
      if (files[i].default === true) defaultImgIdx = i
      formData.append('files', files[i].file)
    }
    formData.append('defaultImgIdx', defaultImgIdx)
    for (let key in params) {
      formData.append(key, params[key])
    }

    return axios.post(cafes.baseUrl,
      formData,
      {headers: {'Content-Type': 'multipart/form-data'}}
    ).then(res => res.data)
  },
  patch: function (params, files) {
    let formData = new FormData()
    let dbFileCount = 0
    for (let i = 0; i < files.length; i++) {
      // DB이미지중 삭제할 이미지
      if (files[i].isDelete === true) {
        formData.append('deleteImgIds', files[i].id)
      }
      // 신규 이미지
      if (files[i].id === null) {
        formData.append('files', files[i].file)
      } else {
        dbFileCount++
      }

      if (files[i].default === true) {
        if (files[i].id === null) {
          formData.append('defaultImgIdx', i - dbFileCount)
        } else {
          formData.append('defaultImgIdxDb', files[i].id)
        }
      }
    }
    // 기존 파라미터
    let keys = ['name', 'memo', 'wifi', 'parking', 'concent', 'reVisit', 'tag', 'address']
    for (let i = 0; i < keys.length; i++) {
      formData.append(keys[i], params[keys[i]])
    }

    for (let i = 0; i < params['tags'].length; i++) {
      formData.append('tags', params['tags'][i])
    }

    return axios.post(cafes.baseUrl + '/' + params.id,
      formData,
      {headers: {'Content-Type': 'multipart/form-data'}}
    ).then(res => res.data)
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
