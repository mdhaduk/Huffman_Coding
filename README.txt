What kinds of file lead to lots of compressions?
    - The types of files that lead to lots of compression include:
        LARGER, more REDUNDANT .txt files. Files such as calagary and
        BooksANDHTML are both rather large text files that compressed
        on average 41%. Interestingly, the largest text file which I
        compressed was the ciaFactbook2008, though it only compressed 
        about 23%. After quickly skimming through the text file, I believe
        this due to the file containing many statistics and numbers instead
        of just words, like the former two files. This would cause the 
        respective HuffmanTree to not contain many short leaf-nodes, or 
        more simply put, short encodings. As a result, the compressed 
        file is not gonna be much smaller, and this proves why REDUNDANT
        .txt files are more apt for lots of compression.

What kind of files had little or no compression?
    - The types of files that lead to little or no compression include:
        SMALLER, UNIQUE .txt and .tif (image) files. Files such as 'smallTxt.txt'
        and waterloo, which contains .tif image files, either negatively
        compressed, meaning the resulting compressed file was actually LARGER
        than the uncompressed file, OR the file's compression was simply marginal.
        It's obvious the 'smallTxt.txt.hf' file will be LARGER because the header
        itself created in the output file is much LARGER than the uncompressed file.
        For waterloo, I believe the reason the image files are compressed very
        marginally is because the file is very random. More specifically, every
        part of the file likely corresponds to a distinct part of the image, and 
        each part will therefore be UNIQUE. Consequently, the HuffmanTree will
        be very tall, not having many leaf nodes with short encodings, resulting
        in marginal compression.

What happens when you try and compress a huffman code file?    
    - When you compress a huffman code file, the total percent  
      compression is very small. I feel this is quite unintuitive,
      but using an example helps me understand this idea better. In
      example, the 'ciaFactbook2008.txt' compresses about 23%. If we 
      compress the resulting 'ciaFactbook2008.txt.hf', what exactly 
      is being compressed? It would be the UNIQUE encodings of '1's and
      '0's that we use to represent REDUNDANT characters in the orginal
      'ciaFactbook2008.txt' file. Consequently, compressing this generally 
      UNIQUE output file should NOT result in a lot of compression, which 
      is quite evident as the 'ciaFactbook2008.txt.hf' file only compressed
      about 1%... LULW.
    

Benchmarking Results:


Files/Directories   Total Bytes Read    Total Compressed Bytes   Total % Compression

smallTxt.txt            26                      1044                -3915.385
calgary                 3251493                 1845571             43.239 
BooksANDHTML            9788581                 5828112             40.460  
waterloo                12466304                10205282            18.137
ciaFactbook2008.txt     15666027                11988591            23.474
ciaFactbook2008.txt.hf  6028799                 5959792             1.145




    
