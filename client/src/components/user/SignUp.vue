<template>
  <div class="hello">
    회원가입

    <p>이메일: <input type="text" v-model="user.email"/></p>
    <p>닉네임: <input type="text" v-model="user.username"/></p>
    <P>비밀번호: <input type="password" v-model="user.password" /></P>
    <input type="button" @click="proc()" value="회원가입"/>
    <p class="error">{{signupErrMsg}}</p>
  </div>
</template>

<script>
import {users} from '@/api/api'
export default {
  name: 'UserSignUp',
  data () {
    return {
      user: {
        email: 'a@a.com',
        username: 'a',
        password: '1'
      },
      signupErrMsg: ''
    }
  },
  created: function () {},
  methods: {
    proc: function () {
      this.singupErrMsg = ''
      users.signUp(this.user).then((res) => {
        if (res === 0) {
          this.$router.push('/')
        } else {
          console.log(res)
          this.signupErrMsg = res
        }
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

.error {
  color:red;
}
</style>
