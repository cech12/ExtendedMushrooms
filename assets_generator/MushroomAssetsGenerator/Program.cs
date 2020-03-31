using System;
using System.IO;
using System.Text;

namespace MushroomAssetsGenerator
{
    class Program
    {

        protected static String TemplateDirectoryPath = "template";
        protected static String ModDirectoryPath = "..\\src\\main\\resources";

        protected static String TemplateReplacement = "glowshroom";

        [STAThread]
        public static void Main()
        {
            if (!Directory.Exists(TemplateDirectoryPath))
            {
                Console.WriteLine("template path not found: " + TemplateDirectoryPath);
                Console.ReadLine();
                return;
            }
            if (!Directory.Exists(ModDirectoryPath))
            {
                Console.WriteLine("mod path not found: " + ModDirectoryPath);
                Console.ReadLine();
                return;
            }

            var templateDirectoryInfo = new DirectoryInfo(TemplateDirectoryPath);
            var modDirectoryInfo = new DirectoryInfo(ModDirectoryPath);
            copyTemplate("poisonous_mushroom", templateDirectoryInfo, modDirectoryInfo);

            Console.WriteLine("SUCCESS");
            Console.ReadLine();
        }


        protected static void copyTemplate(String name, DirectoryInfo templateDirectory, DirectoryInfo modDirectory)
        {
            var templateFiles = templateDirectory.GetFiles();
            foreach (var templateFile in templateFiles)
            {
                //copy file to mod folder
                var filename = templateFile.Name.Replace(TemplateReplacement, name);
                Console.WriteLine($"Copy file: {templateFile.FullName} --> {modDirectory.FullName}\\{filename}");
                FileInfo file = templateFile.CopyTo(modDirectory.FullName + "\\" + filename, true);

                //change content
                var origFileContent = File.ReadAllText(file.FullName, Encoding.Default);
                File.WriteAllText(file.FullName, origFileContent.Replace(TemplateReplacement, name), Encoding.Default);
            }

            var templateDirectories = templateDirectory.GetDirectories();
            foreach (var templatedDir in templateDirectories)
            {
                if (templatedDir.Name.Equals(".git"))
                {
                    continue;
                }
                var modDirectoryPath = modDirectory.FullName + "\\" + templatedDir.Name.Replace(TemplateReplacement, name);
                if (!Directory.Exists(modDirectoryPath))
                {
                    Directory.CreateDirectory(modDirectoryPath);
                    Console.WriteLine("directory created: " + ModDirectoryPath);
                }
                copyTemplate(name, templatedDir, new DirectoryInfo(modDirectoryPath));
            }

        }
    }
}
