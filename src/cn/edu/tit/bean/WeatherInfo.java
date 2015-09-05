package cn.edu.tit.bean;


public class WeatherInfo {
		private String city ;
		private String updatetime ;
		private String wendu ;
		private String fengxiang ;
		private String fengli ;
		private String date ;
		private String time ;
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getUpdatetime() {
			return updatetime;
		}
		public void setUpdatetime(String updatetime) {
			this.updatetime = updatetime;
		}
		public String getWendu() {
			return wendu+"бу";
		}
		public void setWendu(String wendu) {
			this.wendu = wendu;
		}
		public String getFengxiang() {
			return fengxiang;
		}
		public void setFengxiang(String fengxiang) {
			this.fengxiang = fengxiang;
		}
		public String getFengli() {
			return fengli;
		}
		public void setFengli(String fengli) {
			this.fengli = fengli;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		@Override
		public String toString() {
			return "WeatherInfo [city=" + city + ", updatetime=" + updatetime
					+ ", wendu=" + wendu + ", fengxiang=" + fengxiang
					+ ", fengli=" + fengli + ", date=" + date + ", time="
					+ time + "]";
		}
		public String getWeatherSummary(){
			return fengli+fengxiang ;
		}
		
		
		

}
