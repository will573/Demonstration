BEGIN{
  puts "Welcome to the hype recorder."
  puts "This will help you record what you consider positive events that happen around you."

}
  time=Time.new
whatDo="S"

  puts "How would you like to start?"
  while whatDo != "E"
    
  puts "(C)heck Hype events"
  puts "(A)dd Hype events"
  puts "(E)nd program"
  whatDo= gets.chomp
    if whatDo=="C"
      hypeFile = File.open("hype.txt", "r+")
      hypeArr= IO.readlines("hype.txt")
      hypeArr.each do |line|
        puts "#{line}"
        
      hypeFile.close
      end
    elsif whatDo=="A"
      theEvent=""
      puts "Is it (N)ow or (P)ast?"
      eventTime=gets.chomp
      if eventTime=="N"
        theEvent= theEvent + time.month.to_s + "/"
        theEvent= theEvent + time.day.to_s + "/"
        theEvent= theEvent + time.year.to_s + ":"
      elsif eventTime=="P"
        puts"What is the month"
        theEvent= theEvent + gets.chomp + "/"
        puts"What is the day"
        theEvent= theEvent + gets.chomp + "/"
        puts"What is the year"
        theEvent= theEvent + gets.chomp + ":"
      end
      puts "What is this event?"
      theEvent= theEvent + gets.chomp
      
      hypeFile = File.open("hype.txt", 'a')
      hypeFile.write(theEvent+"\n")
      hypeFile.close
    end
    
  end

   
END{
  puts "Farewell. Can't wait to hear more from you." 
}