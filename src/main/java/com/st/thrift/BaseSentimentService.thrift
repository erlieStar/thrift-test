namespace java com.socialtouch.martech.mbasedataprocess.thrift

service BaseSentimentService {

   string getSentimentInfo(1:string text);

}