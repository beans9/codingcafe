<template>
  <div class="hello">
    {{title}}
    <p>카페명*: <input type="text" v-model="cafe.name" />
      <span v-if="cafe.nameErr" class="err">카페명을 입력하십시오</span>
    </p>
    <p>주소*: <input type="text" v-model="cafe.address" placeholder="ex)성남시 분당구, 백현카페거리"/>
      <span v-if="cafe.addressErr" class="err">주소를 입력하십시오</span>
    </p>
    <p>주차정보:
      <label>가능 <input type="radio" name="parking" v-model="cafe.parking" value="1"/></label>
      <label>불가능 <input type="radio" name="parking" v-model="cafe.parking" value="0"/></label>
    </p>
    <p>무선랜정보:
      <label>가능 <input type="radio" name="wifi" v-model="cafe.wifi" value="1"/></label>
      <label>불가능 <input type="radio" name="wifi" v-model="cafe.wifi" value="0"/></label>
    </p>

    <p>전원콘센트 유무:
      <label>있음 <input type="radio" name="concent" v-model="cafe.concent" value="1"/></label>
      <label>없음 <input type="radio" name="concent" v-model="cafe.concent" value="0"/></label>
    </p>
    <p>재방문의사
      <label>있음 <input type="radio" name="reVisit" v-model="cafe.reVisit" value="1"/></label>
      <label>없음 <input type="radio" name="reVisit" v-model="cafe.reVisit" value="0"/></label>
    </p>
    <P>기타설명: <textarea v-model="cafe.memo"></textarea></P>
    <p>태그:
      <input type="text" v-model="tag" placeholder="태그를 입력하십시오.(enter)" v-on:keyup.enter="tagAdd()" />
      <span v-for="(item,index) in tags" v-bind:key="'tag' + index">{{item}} x</span>
    </p>
    <div>사진
      <div v-for="(photo,index) in files" v-bind:key="index" v-bind:class="{active:photo.default}">
        <img :src="photo.image" class="img" @click="changeDefaultImg(photo)"/>
        <input type="button" value="삭제" @click="delFile(photo)" />
        {{photo.default}}
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
        nameErr: false,
        memo: '',
        address: '',
        addressErr: false,
        parking: 0,
        concent: 0,
        wifi: 0,
        reVisit: 0,
        tagList: ''
      },
      tag: '',
      tags: [],
      files: []
    }
  },
  created: function () {
    // this.addFile()
  },
  methods: {
    proc: function () {
      if (this.formValidation()) {
        this.cafe.tagList = this.tags.join('__')
        cafes.insert(this.cafe, this.files).then((res) => {
          setTimeout(() => {
            this.$router.push('/cafe/' + res.id)
          }, 1000)
        })
      }
    },
    formValidation: function () {
      let isCheck = true
      this.cafe.nameErr = false
      this.cafe.addressErr = false

      if (this.cafe.name === '') {
        this.cafe.nameErr = true
        isCheck = false
      }
      if (this.cafe.address === '') {
        this.cafe.addressErr = true
        isCheck = false
      }
      return isCheck
    },
    addFile: function (image, file) {
      let def = this.files.filter((data) => data.default === true).length === 0
      this.files.push({image: image, default: def, file: file})
    },
    delFile: function (photo) {
      if (photo.default === true) {
        if (this.files.length > 0) {
          this.files[0].default = true
        }
      }
      let photoIdx = this.files.indexOf(photo)
      this.files.splice(photoIdx, 1)
    },
    changeDefaultImg: function (photo) {
      this.files.filter((data) => data.default === true)[0].default = false
      photo.default = true
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
          vm.addFile(e.target.result, files[i])
        }
        reader.readAsDataURL(files[i])
      }
    },
    photoUpload: function () {
      this.$refs.file.value = ''
      this.$refs.file.click()
    },
    tagAdd: function () {
      if (this.tag.replace(/ /g, '') === '') {
        return
      }
      this.tags.push(this.tag)
      this.tag = ''
    },
    tagDel: function (item) {
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

.err {
  color:red;
}
</style>
