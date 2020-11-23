//whe notified need to be careful
template<typename Data>
class concurrent_queue3
{
public:
    void push(Data const& data)
    {
        boost::mutex::scoped_lock lock(the_mutex);
        bool const was_empty=the_queue.empty();
        the_queue.push(data);

        lock.unlock(); // unlock the mutex

        if(was_empty)
        {
            the_condition_variable.notify_one();
        }
    }
    // rest as before
};