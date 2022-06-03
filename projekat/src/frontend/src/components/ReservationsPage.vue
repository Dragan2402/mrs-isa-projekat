<template>
  <div class="main-container">
    <div>
      <h3 class="main-heading">Active Reservations</h3>
      <div style="display: flex; align-content: space-between; margin: 15px 0 15px 0">
        <div style="width: 70%">
          <input class="input-group-text" type="text" placeholder="Filter by name, Renting Entity, Price..." v-model="filterR"/>
        </div>
        <br>
        <div style="width: 30%">
          <div style="width: 95%; float: right">
            <select class="form-select" v-model="sortTypeR" name="example">
              <option value=0>Place descending</option>
              <option value=1>Place ascending</option>
              <option value=2>Price descending</option>
              <option value=3>Price ascending</option>
            </select>
          </div>
        </div>
      </div>
    <div class="reservation" v-for="(reservation, index) in this.filteredReservations" v-bind:index="index" :key="reservation.id">
      <div class="reservation-picture"><img v-bind:src="'data:image/jpeg;base64,' + reservation.img"></div>
        <div style="display: flex;">
          <div class="reservation-left">
            <div><h5 style="font-weight: bold;">{{reservation.rentingEntityName}}</h5></div>
            <div><b>Address: </b> {{reservation.place}}</div>
            <div><b>Price: </b> {{reservation.price}}</div>
            <div><b>Starting Date: </b> {{reservation.start}}</div>
            <div><b>Duration: </b> {{msToTime(reservation.duration)}}</div>
            <div v-if="reservation.additionalServices.length != 0"> <b>Additional Services: </b>
              <span v-for="service in reservation.additionalServices.slice(0,-1)" :key="service"> {{service}}, </span>
              <span>{{reservation.additionalServices.at(-1)}}</span>
            </div>
          </div>
          <div class="reservation-right">
              <button class="custom-btn button-primary" @click="cancelReservation(reservation, index)">Cancel Reservation</button>
          </div>
        </div>
      </div>
    </div>
    
    <div>
      <h3 class="main-heading">Reservation History</h3>
      <div style="display: flex; align-content: space-between; margin: 15px 0 15px 0">
        <div style="width: 70%">
          <input class="input-group-text" type="text" placeholder="Filter by name, Renting Entity, Price..." v-model="filterH"/>
        </div>
        <br>
        <div style="width: 30%">
          <div style="width: 95%; float: right">
            <select class="form-select" v-model="sortTypeH" name="example">
              <option value=0>Place descending</option>
              <option value=1>Place ascending</option>
              <option value=2>Price descending</option>
              <option value=3>Price ascending</option>
            </select>
          </div>
        </div>
      </div>
      <div class="reservation" v-for="(reservationHistory, index) in this.filteredReservationsHistory" v-bind:index="index" :key="reservationHistory.id">
        <div class="reservation-picture"><img v-bind:src="'data:image/jpeg;base64,' + reservationHistory.img"></div>
        <div style="display: flex;">
          <div class="reservation-left">
            <div><h5 style="font-weight: bold;">{{reservationHistory.rentingEntityName}}</h5></div>
            <div><b>Address: </b> {{reservationHistory.place}}</div>
            <div><b>Price: </b> {{reservationHistory.price}}</div>
            <div><b>Starting Date: </b> {{reservationHistory.start}}</div>
            <div><b>Duration: </b> {{msToTime(reservationHistory.duration)}}</div>
            <div v-if="reservationHistory.additionalServices.length != 0"> <b>Additional Services: </b>
              <span v-for="service in reservationHistory.additionalServices.slice(0,-1)" :key="service"> {{service}}, </span>
              <span>{{reservationHistory.additionalServices.at(-1)}}</span>
            </div>
          </div>
          <div class="reservation-right">
            <button style="margin-right: 10px" class="custom-btn button-primary" data-bs-toggle="modal" data-bs-target="#modalRate">Rate</button>
            <button class="custom-btn button-primary" data-bs-toggle="modal" data-bs-target="#modalReport">Report</button>
          </div>
        </div>

        <div class="modal fade" id="modalRate" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-body">
                <div>
                  <vue3-star-ratings :showControl=false v-model="ratingRE"/>
                  <textarea v-model="commentRatingRE" style="height: 100px" type="text" class="form-control" placeholder="Comment..."></textarea><br>
                  <button class="custom-btn button-primary" @click="rateRE(reservationHistory)">Rate renting entity</button>
                </div>
                <div>
                  <vue3-star-ratings :showControl=false v-model="ratingRO"/>
                  <textarea v-model="commentRatingRO" style="height: 100px" type="text" class="form-control" placeholder="Comment..."></textarea><br>

                  <button class="custom-btn button-primary" @click="rateRO(reservationHistory)">Rate owner</button>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>

        <div class="modal fade" id="modalReport" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-body">
                <div>
                  <textarea v-model="commentReportRE" style="height: 100px" type="text" class="form-control" placeholder="Comment..."></textarea><br>
                  <button class="custom-btn button-primary" @click="reportRE(reservationHistory)">Submit Report</button>
                </div>
                <div>
                  <br><textarea v-model="commentReportRO" style="height: 100px" type="text" class="form-control" placeholder="Comment..."></textarea><br>
                  <button class="custom-btn button-primary" @click="reportRO(reservationHistory)">Submit Report</button><br>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
              </div>
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
  name: "ReservationsPage",
   data() {
    return {
      reservationsHistory:[],
      selectedReservationHistory:{},
      reservations:[],
      filterR:"",
      filterH: "",
      ratingRE:5,
      commentRatingRE:"",
      commentRatingRO:"",
      ratingRO:5,
      sortTypeR:0,
      sortTypeH: 0,
      commentReportRE:"",
      commentReportRO:"",
    }
  },
  mounted() {
    
    axios.get("/api/clients/loggedClient/reservations",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.reservations=response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);          
          this.$router.push("/loginPage");
        }
       
      });
    axios.get("/api/clients/loggedClient/reservationHistory",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.reservationsHistory=response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);          
          this.$router.push("/loginPage");
        }
       
      });

  },
  computed: {
    filteredReservationsHistory() {
      return this.reservationsHistory.filter(row => {
        return this.includesH(row);
      }).sort((a,b)=>{
        return this.sort(a,b,this.sortTypeH);})
    },
    filteredReservations(){
       return this.reservations.filter(row => {
        return this.includesH(row);
      }).sort((a,b)=>{
        return this.sort(a,b,this.sortTypeR);})
    }},
  methods:{
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
    cancelReservation(reservation, index){
      
      const date=this.dateFromLocal(reservation.start);
      let dateCheck=new Date();
      
      dateCheck.setDate(dateCheck.getDate() + 3);

      if(date < dateCheck){
        this.$toast.error("You can not cancel the reservation within the 3 days");
      }else{
        axios.put("/api/clients/cancelReservation",reservation,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {
          if(response.data == true){
            this.$toast.success("You have successfully canceled the reservation");
            this.reservations.splice(index,1);
          }else{
            this.$toast.error("You can not cancel the reservation within the 3 days");
          }
        })
        
      }
    },

    msToTime(seconds) {     
      seconds = Number(seconds)/1000;
      var d = Math.floor(seconds / (3600*24));
      var h = Math.floor(seconds % (3600*24) / 3600);

      var dDisplay = d > 0 ? d + (d == 1 ? " day " : " days ") : "";
      if (h != 0) {
        dDisplay = d > 0 ? d + (d == 1 ? " day, " : " days, ") : "";
        var hDisplay = h > 0 ? h + (h == 1 ? " hour " : " hours ") : "";
        return dDisplay + hDisplay;
      }

      return dDisplay
  },
  rateRE(reservationHistory){
    const review={"id":0,'rentingEntityId':reservationHistory.rentingEntityId,'rentingOwnerId':reservationHistory.rentingEntityOwnerId,'clientId':0,'rating':this.ratingRE,'comment':this.commentRatingRE};
    axios.post("/api/clients/addReviewRE",review,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Review Added");}else{
      this.$toast.error("Error");
    }})
  },
  rateRO(reservationHistory){
    const review={"id":0,'rentingEntityId':reservationHistory.rentingEntityId,'rentingOwnerId':reservationHistory.rentingEntityOwnerId,'clientId':0,'rating':this.ratingRO,'comment':this.commentRatingRO};
    axios.post("/api/clients/addReviewRO",review,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Review Added");}else{
      this.$toast.error("Error");
    }})

  },
  reportRE(reservationHistory){
    if(this.commentReportRE.length<5){
        this.$toast.error("Provide a comment");
        return;
      }
 
    const complaint={"id":0,"complainantId":0,"rentingEntityId":reservationHistory.rentingEntityId,"respodentId":0,"text":this.commentReportRE};
    axios.post("/api/clients/addComplaintRE",complaint,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Complaint Sent");}else{
          this.$toast.error("Error");
        }})
  },
  reportRO(reservationHistory){
    if(this.commentReportRO.length<5){
        this.$toast.error("Provide a comment");
        return;
      }
    const complaint={"id":0,"complainantId":0,"rentingEntityId":0,"respodentId":reservationHistory.rentingEntityOwnerId,"text":this.commentReportRO};
    axios.post("/api/clients/addComplaintRO",complaint,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Complaint Sent");}else{
          this.$toast.error("Error");
        }})
  },
  includesH(row){
        
        const name = row.place.toString().toLowerCase();
        const rentingEntityName = row.rentingEntityName.toString().toLowerCase();
        const price = row.price.toString().toLowerCase();
        const searchTerm = this.filterH.toLowerCase();
        return (name.includes(searchTerm) || rentingEntityName.includes(searchTerm) || price.includes(searchTerm))
        
    },
    sort(a,b,sortType){
        if(sortType==0){
          let fa = a.place.toLowerCase(), fb = b.place.toLowerCase();
          if (fa < fb) {
            return -1
          }
          if (fa > fb) {
            return 1
          }
          return 0
        }
        else if (sortType== 1){
          let fa = a.place.toLowerCase(), fb = b.place.toLowerCase();
           if (fa > fb) {
            return -1
          }
          if (fa < fb) {
            return 1
          }
          return 0
        }
        if(sortType==2){
        let fa =parseInt(a.price), fb =parseInt(b.price)
        
          if (fa > fb) {
            return -1
          }
          if (fa < fb) {
            return 1
          }
          return 0
        }
        else if (sortType == 3){
          
          let fa = parseInt(a.price), fb =parseInt(b.price);
          
           if (fa < fb) {
            return -1
          }
          if (fa > fb) {
            return 1
          }
          return 0
        }

    },
  }
}
</script>

<style scoped>

.reservation {
  text-align: center;
  width: 100%;
  height: 170px;
  border: 1px solid darkgrey;
  border-radius: 5px;
  overflow: hidden;
  margin-bottom: 10px;
}

.reservation-picture img {
  object-fit: cover;
  margin: 10px;
  border-radius: 5px;
  width: 200px;
  height: 150px;
}

.reservation-picture {
  float:left;
}

.reservation-left {
  float: left;
  margin: 10px;
  text-align: left;
  width: 60%;
}

.reservation-right {
  float: right;
  margin: auto;
  text-align: center;
  width: 30%;
  overflow: hidden;
}
</style>