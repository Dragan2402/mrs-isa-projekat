<template>   
  <div class="main-container" v-if="loaded">
    <h3 class="main-heading">{{rentingEntity.name}}</h3>
    <vue3-star-ratings class="star-ratings" v-model="rentingEntity.rating" starSize="22"  :showControl=false :disableClick=true :step=0 />
    <h5 class="star-heading">({{rentingEntity.reviewsNumber}})</h5>
    <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img class="d-block w-100" alt="Active picture" v-bind:src="'data:image/jpeg;base64,' + pictures.at(0)">
        </div>
        <div class="carousel-item" v-for="(picture, index) in pictures.slice(1)" :key="index">
          <img class="d-block w-100" alt="Item picture" v-bind:src="'data:image/jpeg;base64,' + picture">
        </div>
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>
    <div class="main-description">
      <div class="inner-description">
        <div><b>Address: </b> {{rentingEntity.address}}</div>
        <div><b>Description: </b> {{rentingEntity.promoDescription}}</div>
        <div><b>Behaviour rules: </b> {{rentingEntity.behaviourRules}}</div>
        <div><b>Price: </b> {{rentingEntity.priceList}} per day</div>
        <div><b>Additional info: </b>{{rentingEntity.additionalInfo}}</div>
        <div><b>Cancellation conditions: </b>{{rentingEntity.cancellationConditions}}</div>
        <div v-if="this.displayType==0"><b>Capacity: </b>{{rentingEntity.roomsQuantity}} rooms, {{rentingEntity.bedsPerRoom}} beds per room</div>
        <div v-if="this.displayType==1"><b>Type: </b>{{rentingEntity.type}}</div>
        <div v-if="this.displayType==1"><b>Length: </b>{{rentingEntity.length}}</div>
        <div v-if="this.displayType==1"><b>Engine Number: </b>{{rentingEntity.engineNumber}}</div>
        <div v-if="this.displayType==1"><b>Engine Power: </b>{{rentingEntity.enginePower}}</div>
        <div v-if="this.displayType==1"><b>Top Speed: </b>{{rentingEntity.topSpeed}}</div>
        <div v-if="this.displayType==1"><b>Client Limit: </b>{{rentingEntity.clientLimit}}</div>
        <div v-if="this.displayType==2"><b>Instructor biography: </b>{{rentingEntity.instructorBiography}}</div>
        <div v-if="this.displayType==2"><b>Client limit: </b>{{rentingEntity.clientLimit}}</div>
        <div v-if="!isSubscribed"><button @click="subscribe()">Subscribe</button></div>
        <div v-else @click="unSubscribe()"><button>Unsubscribe</button></div>
      </div>
      <div class="google-map-container">
        <iframe class="google-map" v-bind:src="'https://maps.google.com/maps?q=' + rentingEntity.address + '&t=&z=13&ie=UTF8&iwloc=&output=embed'"></iframe>
      </div>

       
    </div>
    <button @click="toggleMakingReservation" v-if="!makingReservation">Make Reservation</button>
    <div v-if="makingReservation">
          <v-date-picker v-model="range" mode='dateTime' color="blue" is24hr :minute-increment="30" is-range  :min-date=minDate :max-date=maxDate :attributes='attributes' >        
          </v-date-picker>
          <button @click="confirmReservation">Confirm</button>
          <button @click="closeMakingReservation">Cancel</button>
    </div>
    <br>
      
    <hr>
    <div class="main-offer-container">
      <h4 v-if="this.offers.length !== 0">Offers:</h4>
      <div class="inner-offer-container">
        <div class="offer" v-for="(offer, index) in this.offers" 
             v-bind:index="index" :key="offer.id">
          <div class="offer-description">
            <div class="offer-left">
              <div><b>Address: </b> {{offer.place}}</div>
              <div><b>Price: </b> {{offer.price}}</div>
              <div><b>Client limit: </b>{{offer.clientLimit}}</div>
              <div><b>Starting at: </b>{{offer.start}}</div>
              <div><b>Duration: </b>{{msToTime(offer.duration)}}</div>
              <div v-if="offer.additionalServices.length != 0"> <b>Additional Services:</b>
                <div class="additional-service" v-for="service in offer.additionalServices" :key="service"> {{service}}</div>
              </div>
            </div>
            <div class="offer-right">
              <button class="custom-btn button-primary" @click="makeReservation(offer,index)">Make a reservation</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    </div>
</template>

<script>
import axios from "axios"

export default {
  name: "RentingEntityPreview",
  data(){
        let takenDatesPoppers = [];
    return {
      id:0,
      displayType:0,
      rentingEntity: {},
      selectedOffer: {},
      selected:false,
      date:null,
      pictures:[],
      isSubscribed:false,
      index:0,     
      offers: [],
      loaded:false,
      makingReservation:false,
      minDate:null,
      maxDate:null,  
      takenDates:[],
      range: {
        start: new Date(2022, 0, 6,0,0),
        end: new Date(2022, 0, 9,0,0),
      },
      incId: takenDatesPoppers.length,
      takenDatesPoppers,
      bars:[]
    }
  },
  mounted(){
    this.displayType = this.$route.params.displayType;
    this.id=this.$route.params.id;
    this.loaded=false;
    let path,pathOffers,pathPictures;
    if(this.displayType==0 && this.id != undefined){      
      path="/api/vacation_houses/"+this.id;
      pathOffers="/api/vacation_houses/"+this.id+"/offers";
      pathPictures="/api/vacation_houses/"+this.id+"/pictures/all";
      }
    else if(this.displayType==1 && this.id != undefined){
      path="/api/ships/"+this.id;
      pathOffers="/api/ships/"+this.id+"/offers";
      pathPictures="/api/ships/"+this.id+"/pictures/all";
    }
    else if(this.id != undefined){
      path="/api/fishingClasses/"+this.id;
      pathOffers="/api/fishingClasses/"+this.id+"/offers";
      pathPictures="/api/fishingClasses/"+this.id+"/pictures/all";
    }

    axios.get(path).then( response => this.rentingEntity=response.data);


    axios.get(pathPictures).then(response => {
          for (let i = 0; i < response.data.length; i++) {
            this.pictures.push(response.data[i]);
          }
        });
    axios.get(pathOffers).then(response => this.offers=response.data);
    this.loaded=true;

    if(localStorage.getItem("jwt")!="null"){
      if(this.$root.loggedUser.accountType=="CLIENT"){
        const path="/api/clients/isSubscribed/"+this.id;
        axios.get(path,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} } ).then(response =>{
        this.isSubscribed=response.data;
        });
      }
    }

},
computed: {
    attributes() {
      return [
        ...this.bars,       
        // Attributes for todos
        ...this.takenDatesPoppers.map(todo => ({
          dates: todo.dates,
          dot: {
            color: todo.color,
            class: todo.isComplete ? 'opacity-75' : '',
          },
          popover: {
            label: todo.description,
          },
          customData: todo,
        })),
      ];
    },
  },
methods:{
    msToTime(duration) {
    var 
      minutes = Math.floor((duration / (1000 * 60)) % 60),
      hours = Math.floor((duration / (1000 * 60 * 60)) % 24);
    if(minutes>0){
      return hours+"H" + " " + minutes;
    }else{
       return hours+"H";
    }
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
  subscribe(){
    if(localStorage.getItem("jwt")=="null"){
      this.$router.push("/loginPage");
      return;
    }
    if(this.$root.loggedUser.accountType!="CLIENT"){
      this.$toast.error("Only available for clients");
      return;
    }
    axios.put(`/api/clients/subscribe`,this.rentingEntity,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response =>{
      if(response.data==true){
        this.$toast.success("Subscribed");
        this.isSubscribed=true;
      }else{
        this.$toast.error("Error");
        this.isSubscribed=false;
      }
    });
  },
  unSubscribe(){
    if(localStorage.getItem("jwt")=="null"){
      this.$router.push("/loginPage");
      return;
    }
    if(this.$root.loggedUser.accountType!="CLIENT"){
      this.$toast.error("Only available for clients");
      return;
    }
    axios.put(`/api/clients/unSubscribe`,this.rentingEntity,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response =>{
      if(response.data==true){
        this.$toast.success("Unsubscribed");
        this.isSubscribed=false;
      }else{
        this.$toast.error("Error");
        this.isSubscribed=true;
      }
    });
  },
  generateBar(takenDTO){
    let color="red";
    if(takenDTO.type=="Offer"){
      color="blue";
    }
    let datesBars=[]
    let takenTo= this.dateFromLocal(takenDTO.takenTo);
    for (var d = this.dateFromLocal(takenDTO.takenFrom); d <= takenTo; d.setDate(d.getDate() + 1)) {
        const date = new Date(d);
        datesBars.push(date);
    }
    const bar={
   
          dates:datesBars,         
          bar:color,
    };
    this.bars.push(bar);
  },
  generatePopover(takenDTO){
    let color="red";
    if(takenDTO.type=="Offer"){
      color="blue";
    }
    const takenStart={
        description: takenDTO.startsAt,
        isComplete: false,
        dates: this.dateFromLocal(takenDTO.takenFrom), // Every Friday
        color: color,
      };
    const takenEnd={
        description:  takenDTO.endsAt,
        isComplete: false,
        dates: this.dateFromLocal(takenDTO.takenTo), 
        color: color,
      };
    this.takenDatesPoppers.push(takenStart);
    this.takenDatesPoppers.push(takenEnd);
  },
  takeCalendar(takenDates){
    takenDates.forEach(this.generateBar);
    takenDates.forEach(this.generatePopover);
    this.takenDates=takenDates;
  },
  toggleMakingReservation(){
    this.makingReservation=true;
    axios.get(`/api/clients/rentingEntityAvailability/${this.rentingEntity.id}`,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => 
    {
      this.takeCalendar(response.data);
      
      });
    this.minDate = this.dateFromLocal(this.rentingEntity.availableFrom);
    this.maxDate = this.dateFromLocal(this.rentingEntity.availableTo);
    const availableFrom="Renting entity available from "+this.minDate.getHours()+":"+this.minDate.getMinutes();
    const availableTo="Renting entity available till "+this.minDate.getHours()+":"+this.minDate.getMinutes();
    const takenStart={
        description: availableFrom,
        isComplete: false,
        dates: this.minDate, 
        color: "green",
      };
    const takenEnd={
         description: availableTo,
        isComplete: false,
        dates: this.maxDate, 
        color: "green",
      };
    this.takenDatesPoppers.push(takenStart);
    this.takenDatesPoppers.push(takenEnd);

  },
  freeSlot(start,end){
    for (let i = 0; i < this.takenDates.length; i++) {
        const takenFrom=this.dateFromLocal(this.takenDates[i].takenFrom);
        const takenTo = this.dateFromLocal(this.takenDates[i].takenTo);
        if( (start>=takenFrom && end <=takenTo) || (start<=takenFrom && end >=takenTo) || (start>=takenFrom && start<=takenTo) || (end <= takenTo && end >= takenFrom) || start<this.minDate || end>this.maxDate){
          return false;
        } 
    } 
    return true;
  },
  confirmReservation(){
    const start=this.range.start;
    const end=this.range.end;
    if(this.freeSlot(start,end)){
      this.$toast.success("Reservation made");
    }else{
      this.$toast.error("Select free date");
    }
  },
  closeMakingReservation(){
    this.makingReservation=false;
  },

  makeReservation(offer,index){

    if(localStorage.getItem("jwt")=="null"){
      this.$router.push("/loginPage");
      return;
    }
    if(this.$root.loggedUser.accountType!="CLIENT"){
      this.$toast.error("Only available for clients");
      return;
    }
     if(this.$root.loggedUser.penalties==3){
       this.$toast.error("You can not make a reservation. You have 3 penalties. Wait for next month.");
       return;
     }
      axios.post(`/api/offers/makeQuickReservation`,offer,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {
        if(response.data==true){
           this.$toast.success("Reservation made");    
           this.offers.splice(index,1);
        }else{
          this.$toast.error("You can not make a reservation. You have 3 penalties. Wait for next month.");
        }
      });
  }
}
}
</script>

<style scoped>

.carousel-inner {
  border-radius: 5px;
}

.w-100 {
  object-fit: cover;
  height: 500px;
}

div.star-ratings {
  padding: 0;
  margin: 0;
  float: left;
}

.main-container {
  width: 60%;
  margin: auto auto 20% auto;
  min-width: 860px;
}

.main-heading {
  font-weight: bold;
  margin: 10px 0 0 0;
}

.star-ratings {
  margin-right: 5px;
}

.star-heading {
  margin-top: 3px;
}

.main-description {
  display:flex;
  margin-top: 17px;
}

.inner-description {
  margin-right: 50px;
  float:left;
  width: 65%;
  overflow: hidden;
}

.google-map-container {
  display: flex;
  float: right;
  width: 35%;
  overflow: hidden;
}

.google-map {
  width: 400px;
  border-radius: 5px;
}

.inner-offer-container {
  display: grid;
  grid-template-columns: 50% 50%;
  grid-gap: 10px;
}

.offer {
  border: 1px solid darkgrey;
  border-radius: 5px;
}

.offer-description {
  margin: 5px;
  display: flex;
}

.offer-left {
  float: left;
  width: 60%;
}

.additional-service {
  margin-left: 10px;
}

.offer-right {
  float: right;
  width: 40%;
  align-items: center;
  display: flex;
  overflow: hidden;
}

</style>