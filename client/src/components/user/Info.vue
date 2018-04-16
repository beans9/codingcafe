<template>
  <div class="hello">
    회원정보

    <p>아이디: {{user.id}}</p>
    <p>이름: {{user.username}}</p>

  </div>
</template>

<script>
import {users} from '@/api/api'
export default {
  name: 'UserInfo',
  data () {
    return {
      user: {
        id: '',
        username: '',
        password: ''
      }
    }
  },
  created: function () {
    users.getInfo().then(res => {
      this.user = res
    })
  },
  methods: {}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
</style>
