import 'package:flutter/material.dart';
import 'package:have_app/have_app.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final haveApp = HaveApp();

  String text = '';

  Object? result;

  @override
  void initState() {
    super.initState();
  }

  void onChange(String value) {
    setState(() {
      text = value;
    });
  }

  void onTap() async {
    final app = await haveApp.getPackage(GetApplication(packageName: text));

    setState(() {
      result = app.fold((error) => (error), (application) => application);
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('have_app example'),
        ),
        body: SafeArea(
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 20),
            child: Column(
              children: [
                Expanded(
                  child: Center(
                    child: TextField(
                      decoration: const InputDecoration(
                        hintText: 'Type package name',
                      ),
                      onChanged: onChange,
                    ),
                  ),
                ),
                Expanded(
                  child: Center(
                    child: Text(
                      result == null ? 'Do not have a result yet' : result.toString(),
                      textAlign: TextAlign.center,
                    ),
                    
                  ),
                ),
              ],
            ),
          ),
        ),
        bottomNavigationBar: Padding(
          padding: const EdgeInsets.only(
            bottom: 20,
            left: 20,
            right: 20,
          ),
          child: TextButton(
            style: ButtonStyle(
              backgroundColor: MaterialStateProperty.all(
                Theme.of(context).primaryColor,
              ),
            ),
            child: const Padding(
              padding: EdgeInsets.all(8.0),
              child: Text(
                'Search package',
                style: TextStyle(
                  color: Colors.white,
                  fontSize: 18,
                  fontWeight: FontWeight.w400,
                ),
              ),
            ),
            onPressed: text.isEmpty ? null : onTap,
          ),
        ),
      ),
    );
  }
}
