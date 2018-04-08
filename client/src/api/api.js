import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

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

export {cafes}
