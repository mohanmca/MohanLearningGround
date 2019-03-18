import os
import zipfile

source_dir="D:/temp/"
target_dir = 'D:/temp/target'

# Are you sure your files names are capitalized in your zip files?
target_filelist = ['test.txt']

def unzip_and_append_zipname(source_dir, filelist):
    for item in os.listdir(source_dir):  # loop through items in dir
        if item.endswith(".zip"):  # check for ".zip" extension
            zip_file_name = item
            zip_file_name_without_ext = os.path.splitext(item)[0]
            append_to_target= os.path.splitext(item)[0]
            file_path = os.path.join(source_dir, item)  # get zip file path
            print("current zip file >> " + file_path)
            with zipfile.ZipFile(file_path) as zf:  # open the zip file
                for target_file in filelist:  # loop through the list of files to extract
                    if target_file in zf.namelist():  # check if the file exists in the archive
                        # generate the desired output name:
                        target_name = zip_file_name_without_ext + "_" + target_file
                        target_path = os.path.join(target_dir, target_name)  # output path
                        print("Writing output into file >> " + target_path)
                        with open(target_path, "wb") as f:  # open the output path for writing
                            f.write(zf.read(target_file))  # save the contents of the file in it
                    # next file from the list...
        # next zip file...

unzip_and_append_zipname(source_dir, target_filelist)
