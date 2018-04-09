import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import CafeWrite from '@/components/cafe/Write'
import CafeUpdate from '@/components/cafe/Update'
import CafeDetail from '@/components/cafe/Detail'

import UserSignUp from '@/components/user/SignUp'
import UserSignIn from '@/components/user/SignIn'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    // cafes
    {
      path: '/cafe',
      name: 'Cafe',
      component: Home
    },
    {
      path: '/cafe/write',
      name: 'CafeWrite',
      component: CafeWrite
    },
    {
      path: '/cafe/update/:id',
      name: 'CafeUpdate',
      component: CafeUpdate,
      props: true
    },
    {
      path: '/cafe/:id',
      name: 'CafeDetail',
      component: CafeDetail,
      props: true
    },
    // users
    {
      path: '/user/signup',
      name: 'UserSignUp',
      component: UserSignUp
    },
    {
      path: '/user/signin',
      name: 'UserSignIn',
      component: UserSignIn
    }

  ]
})
