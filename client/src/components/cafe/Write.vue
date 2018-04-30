<template>
  <div class="hello">
    {{title}}
    <p>카페명: <input type="text" v-model="cafe.name"/></p>
    <P>설명: <textarea v-model="cafe.memo"></textarea></P>
    <div>사진
      <div :key="'p'+defultImgIdx">
        <div v-for="(photo,index) in files" v-bind:key="index" v-bind:class="{active:photo.default}">
          <img :src="photo.image" class="img" @click="changeDefaultImg(photo)"/>
          <input type="button" value="삭제" @click="delFile(photo)" />
          {{photo.default}}
        </div>

      </div>
      <input type="file" id="file" ref="file" multiple @change="handleFilesUpload($event, files)" style="display:none"/>
      <input type="button" value="사진업로드" @click="photoUpload()" />
    </div>
    <input type="button" @click="proc()" value="저장"/>
  </div>
</template>

<script>
// import _ from 'lodash'
import {cafes} from '@/api/api'
export default {
  name: 'CafeWrite',
  data () {
    return {
      title: '입력',
      cafe: {
        name: '',
        memo: ''
      },
      photos: [],
      photosIdx: 0,
      defultImgIdx: 0,
      files: []

    }
  },
  created: function () {
    this.addFile()
  },
  methods: {
    proc: function () {
      let defaultImgIdx = 0
      for (let i = 0; i < this.photos.length; i++) {
        if (this.photos[i].isDefault === true) {
          defaultImgIdx = i
          break
        }
      }

      cafes.insert(this.cafe, this.files, defaultImgIdx).then((res) => {
        setTimeout(() => {
          this.$router.push('/cafe/' + res.id)
        }, 1000)
      })
    },
    addFile: function (image, def) {
      console.log('add')
      this.files.push({idx: this.photosIdx++, isView: true, image: image, default: def})
    },
    delFile: function (photo) {
      // if (photo.isDefault === true) {
      //   this.photos.filter((data) => data.image !== null)[0].isDefault = true
      // }
      let photoIdx = this.files.indexOf(photo)
      console.log(photoIdx)
      this.files.splice(photoIdx, 1)
    },
    changeDefaultImg: function (photo) {
      console.log(photo)
      photo.default = true
      // this.photos.filter((data) => data.default === true)[0].default = false
      // this.$set(photo, 'default', true)
    },
    handleFilesUpload: function (e, photos) {
      let files = e.target.files || e.dataTransfer.files
      if (!files.length) {
        return
      }

      let vm = this
      for (let i = 0; i < files.length; i++) {
        let reader = new FileReader()
        reader.onload = (e) => {
          files[i].image = e.target.result
          files[i].default = false
          // this.$set(files[i], 'image', e.target.result)
          // console.log(file.images)
          // file.isView = false
          // if (this.photos.filter((data) => data.isDefault === true).length === 0) {
          //   file.isDefault = true
          // }
          // vm.addFile()
          // this.defultImgIdx = 'p' + this.photosIdx++
          // if (this.files.length === 0) file.default = true
          vm.addFile(e.target.result, false)
          // photos.push(files[i])
        }
        reader.readAsDataURL(files[i])
      }
      // this.files = this.files.concat(files)
      // this.files = files
    },
    setFile: function (files) {
      this.files.push(files)
    },
    photoUpload: function () {
      this.$refs.file.value = ''
      this.$refs.file.click()
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

.img {
  width:200px;
  border:1px solid #e6e6e6;
}

.active {
  border:2px solid red;
}
</style>
