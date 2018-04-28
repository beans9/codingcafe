<template>
  <div class="hello">
    {{title}}
    <p>ID: {{id}}</p>
    <p>카페명: <input type="text" v-model="cafe.name"/></p>
    <P>설명: <textarea v-model="cafe.memo"></textarea></P>
    <div>사진
      <div>
        <!-- 기존사진 -->
        <div v-for="(photo,index) in cafe.photos" v-bind:key="'d' + index" v-bind:class="{active:photo.default, delete:photo.isDelete}">
          <img :src="imgSrc(photo.name)" class="img" @click="changeDefaultImg(photo)"/>
          <input type="button" :value="delBtnTitle(photo.isDelete)" @click="delDbFile(photo)" />
          {{photo}}
        </div>

        <div v-for="(photo,index) in photos" v-if="photo.image != null" v-bind:key="index" v-bind:class="{active:photo.isDefault}">
          <img :src="photo.image" class="img" @click="changeDefaultImg(photo)"/>
          <input type="button" value="삭제" @click="delFile(photo)" />
        </div>

      </div>
      <div v-for="(file,index) in photos" v-bind:key="index" >
        <input type="file" id="files" ref="files" @change="handleFilesUpload($event, file)" v-show="file.isView"/>
        <br/>
      </div>
      <!-- <input type="button" value="파일추가" @click="addFile()"/> -->
    </div>

    <input type="button" @click="proc()" value="저장"/>
    <input type="button" @click="back()" value="뒤로"/>
  </div>
</template>

<script>
import {cafes} from '@/api/api'
export default {
  name: 'CafeUpdate',
  props: ['id'],
  data () {
    return {
      title: '수정',
      cafe: {
        id: null,
        name: '',
        memo: ''
      },
      photos: [],
      photosIdx: 0,
      defultImgIdx: 0
    }
  },
  beforeRouteEnter (to, from, next) {
    cafes.get(to.params.id).then((res) => {
      next(vm => { vm.cafe = res })
    }).catch((e) => { next(false) })
  },
  watch: {
    '$route': function (from, to) { this.get() }
  },
  created: function () {
    this.addFile()
  },
  methods: {
    get: function () {
      cafes.get(this.id).then((res) => {
        this.cafe = res
      }).catch((e) => {
        this.$router.push('/')
      })
    },
    proc: function () {
      cafes.patch(this.cafe).then((res) => {
        this.$router.replace('/cafe/' + res.id)
      })
    },
    back: function () {
      this.$router.go(-1)
    },
    addFile: function () {
      this.photos.push({idx: this.photosIdx++, isView: true, image: null, isDefault: false})
    },
    delDbFile: function (photo) {
      this.$set(photo, 'isDelete', !photo.isDelete)
      if (photo.default === true) {
        let dbData = this.cafe.photos.filter((data) => data.isDelete !== true)
        if (dbData.length > 0) {
          dbData[0].default = true
        }
        photo.default = false
      }
    },
    delFile: function (photo) {
      if (photo.isDefault === true) {
        this.photos.filter((data) => data.image !== null)[0].isDefault = true
      }

      let photoIdx = this.photos.indexOf(photo)
      this.photos.splice(photoIdx, 1)
    },
    changeDefaultImg: function (photo) {
      // db 데이터 이거나, 신규 추가 데이터 이거나
      let dbData = this.cafe.photos.filter((data) => data.default === true)
      let newData = this.photos.filter((data) => data.isDefault === true)
      if (dbData.length > 0) {
        dbData[0].default = false
      }
      if (newData.length > 0) {
        newData[0].isDefault = false
      }
      photo.default = true
    },
    handleFilesUpload: function (e, file) {
      var files = e.target.files || e.dataTransfer.files
      if (!files.length) {
        return
      }
      // let image = new Image()
      let reader = new FileReader()
      let vm = this
      reader.onload = (e) => {
        file.image = e.target.result
        file.isView = false
        if (this.photos.filter((data) => data.isDefault === true).length === 0) {
          file.isDefault = true
        }
        vm.addFile()
      }
      reader.readAsDataURL(files[0])
    },
    imgSrc: function (fileName) {
      return require('@/assets/images/' + fileName)
    },
    delBtnTitle: function (flag) {
      return flag ? '취소' : '삭제'
    }
  },
  computed: {}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

.img {
  width: 200px;
}

.active {
  border:2px solid red;
}

.delete {
  opacity: 0.5;
}
</style>
