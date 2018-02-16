//import fetch from 'node-fetch';
import 'whatwg-fetch';

async function* streamAsyncIterator(stream) {
    console.log(stream)
    // Get a lock on the stream
    const reader = stream.getReader();
  
    try {
      while (true) {
        // Read from the stream
        const {done, value} = await reader.read();
        // Exit if we're done
        if (done) return;
        // Else yield the chunk
        yield value;
      }
    }
    finally {
      reader.releaseLock();
    }
  }

async function example() {
    const find = 'J';
    const findCode = find.codePointAt(0);
    const response = await fetch('https://html.spec.whatwg.org');
    let bytes = 0;
  
    for await (const chunk of streamAsyncIterator(response.body)) {
      const index = chunk.indexOf(findCode);
  
      if (index != -1) {
        bytes += index;
        console.log(`Found ${find} at byte ${bytes}.`);
        break;
      }
  
      bytes += chunk.length;
    }
  
    response.body.cancel();
  }

  example();