//Waiting with a Condition Variable
template<typename Data>
class concurrent_queue1
{
private:
    boost::condition_variable the_condition_variable;
public:
    void wait_for_data()
    {
        boost::mutex::scoped_lock lock(the_mutex);
        while(the_queue.empty())
        {
            the_condition_variable.wait(lock);
        }
    }
    void push(Data const& data)
    {
        boost::mutex::scoped_lock lock(the_mutex);
        bool const was_empty=the_queue.empty();
        the_queue.push(data);
        if(was_empty)
        {
            the_condition_variable.notify_one();
        }
    }
    // rest as before
};