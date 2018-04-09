<template>
  <div class="hello">
    로그인

    <p>아이디: <input type="text" v-model="user.username"/></p>
    <P>비밀번호: <input type="password" v-model="user.password" /></P>
    <input type="button" @click="proc()" value="로그인"/>
  </div>
</template>

<script>
import {users} from '@/api/api'
export default {
  name: 'UserSignIn',
  data () {
    return {
      user: {
        username: '',
        password: ''
      }
    }
  },
  created: function () {},
  methods: {
    proc: function () {
      users.signIn(this.user).then((res) => {
        console.log(res)
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
</style>
