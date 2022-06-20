<template>
  <div class="main-container">
    <div class="left-bar">
      <h5 style="color: white; font-weight: bold">Search</h5>
      <div class="left-bar-text" style="margin-top: 0">Destination name:</div>
      <div>
        <input class="input-group-text" type="text" v-model="nameFilter"/>
      </div>
      <div class="left-bar-text">Destination address:</div>
      <div>
        <input class="input-group-text" type="text"  v-model="addressFilter"/>
      </div>
      <div class="left-bar-text">Date:</div>
      <Datepicker v-model="availabilityInterval" :format="formatRange" range clearable/>
      <div class="left-bar-text">People:</div>
      <input type="number" class="input-group-text" style="text-align: left" :min=0 :max=50 :step=1 v-model="people"/><br>
      <Slider style="width: 95%; margin: auto" v-model="people" :min=0 :max=50 :width=100 :step=1 :tooltips=false />
      <div class="left-bar-text">Price &euro;:</div>
      <div style="display: block; align-content: space-between; text-align: center; min-height: 38px">
      <input class="input-group-text" style="width: 45%; float: left" type="number"  :min=0 :max=price[1] :step=50  v-model="price[0]"/>
      <label style="color:white; margin-top: 5px">-</label>
      <input class="input-group-text" style="width: 45%; float: right" type="number" :min=price[0] :max=1000 :step=50 v-model="price[1]"/>
      </div><br>
      <Slider style="width: 95%; margin: auto" v-model="price" :min=0 :max=1000 :width=100 :step=50 :tooltips=false />
      <div class="left-bar-text">Sort by:</div>
      <div class="sort">
        <select class="form-select" v-model="sortType" name="example">
          <option value=0>Name descending</option>
          <option value=1>Name ascending</option>
          <option value=2>Price descending</option>
          <option value=3>Price ascending</option>
        </select>
      </div>
      <div class="sort" >
          <div class="left-bar-text">Group by:</div> 
          <select class="form-select" v-model="groupBy">
            <option value=1>1</option>
            <option value=5>5</option>
            <option value=10>10</option>
            <option value=15>15</option>
          </select>
        </div>
      <br>
      <div class="radio-div">
        <div>
          <input class="radio" type="radio" id="contactChoice1" name="choice" value=0 v-model="toDisplay">
          <label class="custom-btn button-primary" for="contactChoice1">Houses</label>
        </div>
        <div>
          <input class="radio" type="radio" id="contactChoice2" name="choice" value=1 v-model="toDisplay">
          <label class="custom-btn button-primary" for="contactChoice2">Boats</label>
        </div>
        <div>
          <input class="radio" type="radio" id="contactChoice3" name="choice" value=2 v-model="toDisplay">
          <label class="custom-btn button-primary" for="contactChoice3">Fishing classes </label>
        </div>
      </div>
      <div>
        <button style="margin-bottom: 7px" class="custom-btn button-secondary" @click="search(true)">Search</button>
      </div>
    </div>
    <div class="home-entities-div">
      <div >
        <div class="list-entities" v-for="(vacationHouse, index) in this.entities" @Click="selectEntity(vacationHouse)"
            v-bind:index="index" :key="vacationHouse.id" v-bind="{selected: selectedEntity.id===vacationHouse.id}">
          <div class="entity-picture"><img v-bind:src="'data:image/jpeg;base64,' + vacationHouse.img" @click="jumpToPreview(vacationHouse)" style="width: 200px; height: 150px; cursor: pointer"></div>
          <div class="entity-name"><h4 @click="jumpToPreview(vacationHouse)" style="font-weight: bold; cursor: pointer">{{ vacationHouse.name }}</h4>
            <div class="entity-description"><i class="bi bi-geo-alt-fill"></i> <b>{{ vacationHouse.address }}</b> </div>
            <div class="entity-description">{{ vacationHouse.promoDescription }}</div>
            <vue3-star-ratings class="star-ratings" v-model="vacationHouse.rating" starSize="15"  :showControl=false :disableClick=true :step=0 />
            <span style="color: #585858;">({{vacationHouse.reviewsNumber}})</span>
          </div>
          <div class="entity-price">
            <div><h5 style="font-weight: bold">{{ vacationHouse.priceList }}&euro;</h5></div>
            <div>
              <button class="custom-btn button-primary" @click="jumpToPreview(vacationHouse)">Explore</button>
            </div>
          </div>
        </div>
      </div>
      <div style="text-align: center">
        <button class="btn btn-light" @click="previousPage()">Prev</button>
        {{displayCurrentPage}}
        <button class="btn btn-light"  @click="nextPage()">Next</button>
      </div>
      <br><br><br><br><br><br>
    </div>
  </div>
</template>

<script>
import axios from "axios"
import Slider from '@vueform/slider'

export default {

  name: "HomePage",
  components: {
      Slider,
    },
  data() {
    return {
      publicPath: process.env.BASE_URL,
      entities: [],
      selectedEntity: {},
      selectedRows: [],
      people:0,
      currentPage:0,
      displayCurrentPage:0,
      maximumPage:null,
      groupBy:5,
      price:[0,1000],
      availabilityInterval: null,
      filterData:{
        "type":"vacationHouses",
        "name":"",
        "address":"",
        "start":null,
        "end":null,
        "people":0,
        "priceMin":0,
        "priceMax":100
      },
      sortType: 0, //represents type of sort, 0 name desc, 1 name asc, 2 price asc, 3 price desc
      nameFilter: "",
      addressFilter:"",
      selected: false,
      toDisplay:0,
      displayType: 0,  //represents type of renting entity, 0 vac houses, 1 ships , 2 fishing classes
    }
  }
  ,
  mounted() {
    // const name="";
    // const address="";
    // const start=new Date(2022,3,1,23,0);
    // const end=new Date(2022,7,8,15,0);
    // const people=8;
    // const priceMin=40;
    // const priceMax=800;
    // console.log(start.toISOString());
    // console.log(end.toISOString());
    axios.get(`https://renting-buddy-spring.herokuapp.com/api/vacationHouses/anyUser/?page=${this.currentPage}&size=5&sort=name,ASC`).then(response => this.entities=response.data);
    //axios.get("api/fishingClasses/all").then(response => this.fishingClasses = response.data);
    //axios.get("api/ships/all").then(response => this.ships = response.data);

  },
  
  methods: {       

    formatRange(dates){
      let from = dates[0];
      let to = dates[1];
      if(from==null){
        from=new Date()
      }
      const dayFrom = from.getDate();
      const monthFrom = from.getMonth() + 1;
      const yearFrom = from.getFullYear();
      const hourFrom = from.getHours();
      const minuteFrom = from.getMinutes();

      if(to==null){
        to=new Date()
      }
      const dayTo = to.getDate();
      const monthTo = to.getMonth() + 1;
      const yearTo = to.getFullYear();
      const hourTo = to.getHours();
      const minuteTo = to.getMinutes();

      return `${dayFrom}.${monthFrom}.${yearFrom} ${hourFrom}:${minuteFrom} - ${dayTo}.${monthTo}.${yearTo} ${hourTo}:${minuteTo}`
    },
    selectEntity(entity) {

      this.selectedEntity = entity;
      this.selected = true;

    },
    previousPage(){
      if((this.currentPage-1)<=0){
        this.currentPage=0;
      }else{
        this.currentPage=this.currentPage-1;
      }
      this.search(false);
    },
    nextPage(){
      this.currentPage=this.currentPage+1;
      this.search(false);
    },
    generateUrl(){
        let url="https://renting-buddy-spring.herokuapp.com/api/"+this.filterData.type+"/anyUser/?";
        if(this.filterData.name!=""){
          url=url+"&name="+this.filterData.name;
        }
        if(this.filterData.address!=""){
          url=url+"&address="+this.filterData.address;
        }
        if(this.filterData.start!=null){
          url=url+"&start="+this.filterData.start;
        }

        if(this.filterData.end!=null){
          url=url+"&end="+this.filterData.end;
        }

        url=url+"&people="+this.filterData.people
        +"&priceMin="+this.filterData.priceMin+"&priceMax="+this.filterData.priceMax+"&page="+this.currentPage+"&size="+this.groupBy+
        this.getSortType();

        return url;
    },
    
    getSortType(){
      //0 name desc, 1 name asc, 2 price asc, 3 price desc
      //"&sort=name,ASC"
      if(this.sortType==0){
        return "&sort=name,DESC";
      }
      else if(this.sortType==1){
        return "&sort=name,ASC";
      }else if(this.sortType==3){
        return "&sort=priceList,ASC";
      }else{
        return "&sort=priceList,DESC";
      }
    },
    getType(){
      if(this.toDisplay==0){
        return "vacationHouses";
      }else if(this.toDisplay==1){
        return "ships";
      }else{
        return "fishingClasses";
      }
    },
    search(fromButton){
      if(fromButton){
        this.filterData.type=this.getType();
        this.filterData.name=this.nameFilter;
        this.filterData.address=this.addressFilter;
        if(this.availabilityInterval != null){
          this.filterData.start=this.availabilityInterval[0].toISOString();
          if(this.availabilityInterval[1]!=null){
            this.filterData.end=this.availabilityInterval[1].toISOString();
          }else{
            this.filterData.end=null;
            this.filterData.start=null;
          }
        }else{
          this.filterData.start=null;
          this.filterData.end=null;
        }

        this.filterData.people=this.people;
        this.filterData.priceMin=this.price[0];
        this.filterData.priceMax=this.price[1];
        this.displayType=this.toDisplay;
        this.currentPage=0;
      }
      const url=this.generateUrl();
   
      axios.get(url).then(response => {
        
        if(response.data.length==0){
          if(fromButton){
            this.$toast.error("No results found");
          }
          if(!fromButton){
          this.currentPage=this.currentPage-1; 
          }
         
        }else{
          this.entities=response.data;
          this.displayCurrentPage=this.currentPage;
        }
        });
    },
    dateFromLocal(dateString){
      if(dateString==null){
        return new Date();
      }
      
      const parts=dateString.split(" ");
      
      if( parts.length !=2){
        return new Date();
      }
      const dateParts= parts[0].split(".");
      
      if( dateParts.length !=3){
        return new Date();
      }
      const hourParts=parts[1].split(":");
      
      if( hourParts.length !=2){
        return new Date();
      }
      
      const day = dateParts[0];
      const month = dateParts[1];
      const year = dateParts[2];
      const hours = hourParts[0];
      const minutes = hourParts[1];
      // new Date(year, month, day, hours, minutes, seconds, milliseconds)
      const date= new Date(year,month-1,day,hours,minutes);
      return date;
    },
    
   
    jumpToPreview(entity) {
      this.selectEntity(entity);
      this.$router.push({
        name: "rentingEntityPreview",
        params: {id: this.selectedEntity.id, displayType: this.displayType}
      });
    }

  }
}
</script>


<style scoped>

.input-group-text {
  width: 100%;
  margin: auto;
}

.list-entities {
  text-align: center;
  width: 100%;
  height: 170px;
  border: 1px solid darkgrey;
  border-radius: 5px;
  overflow: hidden;
  margin-bottom: 10px;
}

.datepicker-sort select {
  margin-left: 30px;
  width: 200px;
}

.entity-picture img {
  object-fit: cover;
  margin: 10px;
  border-radius: 5px;
  width: 200px;
  height: 150px;
}

.entity-picture {
  float:left;
}

.entity-name {
  float: left;
  margin: 10px;
  text-align: left;
}

.entity-description {
  color: #585858;
  max-width: 280px;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.entity-price {
  float: right;
  margin: 50px 30px 0 0;
  min-width: 150px;
}

.radio {
  opacity: 0;
  width: 0;
  height: 0;
}

.button-primary, .button-secondary {
  cursor: pointer;
  width: 100%;
  margin-bottom: 25px;
}

[type=radio]:checked + label {
  background-color: #004661;
}

</style>